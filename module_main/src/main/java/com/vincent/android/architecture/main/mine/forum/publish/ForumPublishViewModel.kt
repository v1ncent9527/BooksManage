package com.vincent.android.architecture.main.mine.forum.publish

import android.app.Application
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.drake.channel.sendEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.main.model.ForumModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
class ForumPublishViewModel(application: Application) : BaseViewModel(application) {
    val title = StringObservableField("")
    val content = StringObservableField("")


    val sendClick = BindingClick {
        if (title.get().isEmpty() || title.get().isEmpty()) return@BindingClick

        loading()
        val focusRecordModel = ForumModel(
            System.currentTimeMillis(),
            userModel!!.id,
            userModel!!.nickname,
            title.get(),
            content.get(),
            System.currentTimeMillis(),
        )
        focusRecordModel.save(object : SaveListener<String>() {
            override fun done(objectId: String?, e: BmobException?) {
                hideLoading()
                if (!objectId.isNullOrEmpty()) {
                    toast("发布成功！")
                    sendEvent("success", C.BusTAG.FORUM_PUBLISH)
                    finish()
                } else {
                    toast(e?.message!!)
                }
            }
        })
    }
}