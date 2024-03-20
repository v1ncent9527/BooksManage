package com.vincent.android.architecture.base.extention

import ando.file.androidq.FileOperatorQ
import ando.file.compressor.ImageCompressPredicate
import ando.file.compressor.ImageCompressor
import ando.file.compressor.OnImageCompressListener
import ando.file.compressor.OnImageRenameListener
import ando.file.core.FileDirectory
import ando.file.core.FileLogger
import ando.file.core.FileUtils
import ando.file.selector.FileSelectCallBack
import ando.file.selector.FileSelectCondition
import ando.file.selector.FileSelectOptions
import ando.file.selector.FileSelectResult
import ando.file.selector.FileSelector
import ando.file.selector.FileType
import ando.file.selector.IFileType
import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toFile
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.Utils
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：文件操作扩展
 * 修订历史：
 * ================================================
 */
const val REQUEST_CHOOSE_IMAGE = 10
const val REQUEST_CHOOSE_FILE = 11

/**
 * bitmap保存为file
 */
fun Bitmap.toFile(
    fileName: String = "IMG_${System.currentTimeMillis()}.jpeg",
    quality: Int = 100
): File {
    val filePath =
        ContextWrapper(Utils.getApp()).getExternalFilesDir("Pictures")

    val file = File(filePath, fileName)
    val bos = BufferedOutputStream(
        FileOutputStream(file)
    )
    compress(Bitmap.CompressFormat.JPEG, quality, bos)
    bos.flush()
    bos.close()
    return file
}

/**
 * 应用缓存目录(App cache directory) :
 *  1. 压缩图片后的缓存目录
 *
 * 注: 手机文件管理器看不到, 需要用AS自带的Device File Explorer查看
 *
 * path:  /data/data/com.ando.file.sample/cache/image/
 */
fun getCompressedImageCacheDir(): String {
    val path = "${FileDirectory.getCacheDir().absolutePath}/image/"
    val file = File(path)
    return if (file.mkdirs()) path else path
}

/**
 * 压缩图片 1.Luban算法; 2.直接压缩 -> val bitmap:Bitmap=ImageCompressEngine.compressPure(uri)
 *
 * T : String.filePath / Uri / File
 */
fun <T> compressImage(context: Context, photos: List<T>, success: (index: Int, uri: Uri?) -> Unit) {
    ImageCompressor
        .with(context)
        .load(photos)
        .ignoreBy(50)//单位 Byte
        .setTargetDir(getCompressedImageCacheDir())
        .setFocusAlpha(false)
        .enableCache(true)
        .filter(object : ImageCompressPredicate {
            override fun apply(uri: Uri?): Boolean {
                //FileLogger.i("compressImage predicate $uri  ${FileUri.getFilePathByUri(uri)}")
                return if (uri != null) !FileUtils.getExtension(uri).endsWith("gif") else false
            }
        })
        .setRenameListener(object : OnImageRenameListener {
            override fun rename(uri: Uri?): String? {
                try {
                    val fileName = FileUtils.getFileNameFromUri(uri)
                    val md = MessageDigest.getInstance("MD5")
                    md.update(fileName?.toByteArray() ?: return "")
                    return BigInteger(1, md.digest()).toString(32)
                } catch (e: NoSuchAlgorithmException) {
                    FileLogger.e("rename onError ${e.message}")
                }
                return ""
            }
        })
        .setImageCompressListener(object : OnImageCompressListener {
            override fun onStart() {}
            override fun onSuccess(index: Int, uri: Uri?) {
                success.invoke(index, uri)
            }

            override fun onError(e: Throwable?) {
                FileLogger.e("compressImage onError ${e?.message}")
            }
        }).launch()
}

/**
 * 图片选择 单选
 */
fun selectSingleImage(
    context: Context,
    success: (index: Int, uri: Uri?, file: File?) -> Unit
): FileSelector? {
    var fileSelector: FileSelector? = null

    PermissionUtils.permission(PermissionConstants.STORAGE)
        .callback(object : PermissionUtils.FullCallback {
            override fun onGranted(granted: MutableList<String>) {
                val optionsImage = FileSelectOptions().apply {
                    fileType = FileType.IMAGE
                    fileTypeMismatchTip = "File type mismatch !"
                    singleFileMaxSize = 10485760
                    singleFileMaxSizeTip = "The largest picture does not exceed 10M !"

                    fileCondition = object : FileSelectCondition {
                        override fun accept(fileType: IFileType, uri: Uri?): Boolean {
                            //Filter out gif
                            return (fileType == FileType.IMAGE && uri != null
                                    && !uri.path.isNullOrBlank() && !FileUtils.isGif(uri))
                        }
                    }
                }

                fileSelector = FileSelector
                    .with(context)
                    .setRequestCode(REQUEST_CHOOSE_IMAGE)
                    .setMimeTypes("image/*")
                    .applyOptions(optionsImage)

                    //优先使用 FileSelectOptions 中设置的 FileSelectCondition
                    .filter(object : FileSelectCondition {
                        override fun accept(fileType: IFileType, uri: Uri?): Boolean {
                            return when (fileType) {
                                FileType.IMAGE -> (uri != null && !uri.path.isNullOrBlank() && !FileUtils.isGif(
                                    uri
                                ))

                                FileType.VIDEO -> false
                                FileType.AUDIO -> false
                                else -> false
                            }
                        }
                    })
                    .callback(object : FileSelectCallBack {
                        override fun onSuccess(results: List<FileSelectResult>?) {
                            if (!results.isNullOrEmpty()) {
                                val uri = results[0].uri
                                val photos = listOf(uri)
                                compressImage(context, photos) { index, u ->
                                    val bitmap: Bitmap? = FileOperatorQ.getBitmapFromUri(u)
                                    val file = bitmap?.toFile()
                                    bitmap?.recycle()
                                    success.invoke(index, u, file)
                                }
                            }
                        }

                        override fun onError(e: Throwable?) {
                            toast(e?.message!!)
                        }
                    })
                    .choose()

            }

            override fun onDenied(
                deniedForever: MutableList<String>,
                denied: MutableList<String>
            ) {
                toast("权限不可用，请开启存储权限")
            }
        }).request()
    return fileSelector
}

/**
 * 图片选择 单选
 */
fun selectMultiImage(
    context: Context,
    minCount: Int = 1,
    maxCount: Int = 9,
    success: (multiImageList: List<MultiImageModel>) -> Unit
): FileSelector? {
    var fileSelector: FileSelector? = null

    PermissionUtils.permission(PermissionConstants.STORAGE)
        .callback(object : PermissionUtils.FullCallback {
            override fun onGranted(granted: MutableList<String>) {
                val optionsImage = FileSelectOptions().apply {
                    fileType = FileType.IMAGE
                    fileTypeMismatchTip = "File type mismatch !"
                    singleFileMaxSize = 10485760
                    singleFileMaxSizeTip = "The largest picture does not exceed 10M !"
                    allFilesMaxSize = 10485760 * maxCount.toLong()
                    fileCondition = object : FileSelectCondition {
                        override fun accept(fileType: IFileType, uri: Uri?): Boolean {
                            //Filter out gif
                            return (fileType == FileType.IMAGE && uri != null
                                    && !uri.path.isNullOrBlank() && !FileUtils.isGif(uri))
                        }
                    }
                }

                fileSelector = FileSelector
                    .with(context)
                    .setMultiSelect()
                    .setRequestCode(REQUEST_CHOOSE_IMAGE)
                    .setMinCount(minCount, "Choose at least one picture")
                    .setMaxCount(maxCount, "Choose at least nine picture")
                    .setSingleFileMaxSize(10485760, "")
                    .setAllFilesMaxSize(10485760 * maxCount.toLong(), "")
                    .setMimeTypes("image/*")
                    .applyOptions(optionsImage)

                    //优先使用 FileSelectOptions 中设置的 FileSelectCondition
                    .filter(object : FileSelectCondition {
                        override fun accept(fileType: IFileType, uri: Uri?): Boolean {
                            return when (fileType) {
                                FileType.IMAGE -> (uri != null && !uri.path.isNullOrBlank() && !FileUtils.isGif(
                                    uri
                                ))

                                FileType.VIDEO -> false
                                FileType.AUDIO -> false
                                else -> false
                            }
                        }
                    })
                    .callback(object : FileSelectCallBack {
                        override fun onSuccess(results: List<FileSelectResult>?) {
                            if (!results.isNullOrEmpty()) {
                                val photos = mutableListOf<Uri>().apply {
                                    results.forEach { _FileSelectResult ->
                                        add(_FileSelectResult.uri!!)
                                    }
                                }
                                var count = 0
                                val multiImageList = mutableListOf<MultiImageModel>()
                                compressImage(context, photos) { index, u ->
                                    val bitmap: Bitmap? = FileOperatorQ.getBitmapFromUri(u)
                                    val file = bitmap?.toFile()
                                    bitmap?.recycle()
                                    count++
                                    multiImageList.add(
                                        MultiImageModel(
                                            index = index,
                                            uri = u!!,
                                            file = file!!
                                        )
                                    )
                                    if (count == photos.size) {
                                        success.invoke(multiImageList)
                                    }
                                }
                            }
                        }

                        override fun onError(e: Throwable?) {
                            toast(e?.message!!)
                        }
                    })
                    .choose()

            }

            override fun onDenied(
                deniedForever: MutableList<String>,
                denied: MutableList<String>
            ) {
                toast("权限不可用，请开启存储权限")
            }
        }).request()
    return fileSelector
}

data class MultiImageModel(
    val index: Int,
    val uri: Uri,
    val file: File,
)

/**
 * uri转file，兼容android11
 *
 * @param context
 * @param uri
 */
fun uriToFile(context: Context, uri: Uri): File? = when (uri.scheme) {
    ContentResolver.SCHEME_FILE -> uri.toFile()
    ContentResolver.SCHEME_CONTENT -> {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val ois = context.contentResolver.openInputStream(uri)
                val displayName =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                ois?.let { _inoutStream ->
                    val file = File(
                        context.externalCacheDir!!.absolutePath,
                        displayName
                    )
                    val fos = FileOutputStream(file)
                    val buffer = ByteArray(1024)
                    var len: Int = ois.read(buffer)
                    while (len != -1) {
                        fos.write(buffer, 0, len)
                        len = ois.read(buffer)
                    }
                    fos.close()
                    _inoutStream.close()
                    file
                }
            } else {
                it.close()
                null
            }
        }
    }

    else -> null
}

