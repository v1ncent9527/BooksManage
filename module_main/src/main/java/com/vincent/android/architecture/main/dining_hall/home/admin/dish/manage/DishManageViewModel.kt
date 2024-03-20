package com.vincent.android.architecture.main.dining_hall.home.admin.dish.manage

import android.app.Application
import androidx.lifecycle.scopeNetLife
import com.drake.net.Post
import com.drake.net.convert.NetConverter
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.LiveDataEvent
import org.json.JSONObject
import java.io.File

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
class DishManageViewModel(application: Application) : BaseViewModel(application) {

    val imgUploadUrlObserver = LiveDataEvent<String>()
    fun uploadPic(file: File) {
        loading()
        scopeNetLife {
            Post<String>("https://www.imgtp.com/api/upload") {
                converter = NetConverter.DEFAULT
                param("image", file)
            }.await().apply {
                imgUploadUrlObserver.value =
                    JSONObject(this).optJSONObject("data")?.optString("url")
            }
        }.finally {
            hideLoading()
            it?.let { handleError(it) }
        }
    }
}