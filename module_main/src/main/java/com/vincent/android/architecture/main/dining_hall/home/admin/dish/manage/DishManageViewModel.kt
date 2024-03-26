package com.vincent.android.architecture.main.dining_hall.home.admin.dish.manage

import android.app.Application
import androidx.lifecycle.scopeNetLife
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.blankj.utilcode.util.ObjectUtils
import com.drake.channel.sendEvent
import com.drake.net.Post
import com.drake.net.convert.NetConverter
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.LiveDataEvent
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.main.dining_hall.model.DishModel
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
    val name = StringObservableField("")
    val desc = StringObservableField("")
    var type = 0
    val typeName = StringObservableField("")
    val materials = StringObservableField("")
    val price = StringObservableField("")
    val costPrice = StringObservableField("")

    var dishModel: DishModel? = null
    var objectId: String? = null

    val commitClick = BindingClick {
        if (name.get().isEmpty() || desc.get().isEmpty() || typeName.get()
                .isEmpty() || materials.get().isEmpty() || price.get().isEmpty() || costPrice.get()
                .isEmpty() || imgUrl.isEmpty()
        ) {
            toast("请填写所有信息后再提交！")
            return@BindingClick
        }

        loading()
        if (ObjectUtils.isEmpty(dishModel)) {
            val dishModel = DishModel(
                System.currentTimeMillis(),
                name.get(),
                desc.get(),
                type,
                imgUrl,
                materials.get(),
                price.get().toDouble(),
                costPrice.get().toDouble(),
            )
            dishModel.save(object : SaveListener<String>() {
                override fun done(objectId: String?, e: BmobException?) {
                    hideLoading()
                    if (!objectId.isNullOrEmpty()) {
                        toast("上新成功！")
                        sendEvent("success", C.BusTAG.DISH_UPDATE)
                        finish()
                    } else {
                        toast(e?.message!!)
                    }
                }
            })
        } else {
            val dishModelUpdate = DishModel(
                dishModel!!.id,
                name.get(),
                desc.get(),
                type,
                imgUrl,
                materials.get(),
                price.get().toDouble(),
                costPrice.get().toDouble(),
            )
            dishModelUpdate.update(objectId, object : UpdateListener() {
                override fun done(e: BmobException?) {
                    hideLoading()
                    if (ObjectUtils.isEmpty(e)) {
                        toast("编辑成功！")
                        sendEvent("success", C.BusTAG.DISH_UPDATE)
                        finish()
                    } else {
                        toast(e?.message!!)
                    }
                }
            })
        }

    }

    /**
     * 上传图片
     */
    val imgUploadUrlObserver = LiveDataEvent<String>()
    var imgUrl = ""
    fun uploadPic(file: File) {
        loading()
        scopeNetLife {
            Post<String>("https://www.imgtp.com/api/upload") {
                converter = NetConverter.DEFAULT
                param("image", file)
            }.await().apply {
                JSONObject(this).optJSONObject("data")?.optString("url")?.let {
                    imgUrl = it
                    imgUploadUrlObserver.value = it
                }
            }
        }.finally {
            hideLoading()
            it?.let { handleError(it) }
        }
    }
}