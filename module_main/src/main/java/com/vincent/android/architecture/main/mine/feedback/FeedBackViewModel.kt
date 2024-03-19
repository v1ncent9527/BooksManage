package com.vincent.android.architecture.main.mine.feedback

import android.app.Application
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.main.model.FeedBackModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
class FeedBackViewModel(application: Application) : BaseViewModel(application) {

    val title = StringObservableField("")
    val content = StringObservableField("")


    val sendClick = BindingClick {
        if (title.get().isEmpty() || title.get().isEmpty()) return@BindingClick

        loading()
        val feedBackModel = FeedBackModel(
            System.currentTimeMillis(),
            userModel!!.id,
            userModel!!.nickname,
            title.get(),
            content.get(),
            System.currentTimeMillis(),
        )
        feedBackModel.save(object : SaveListener<String>() {
            override fun done(objectId: String?, e: BmobException?) {
                hideLoading()
                if (!objectId.isNullOrEmpty()) {
                    toast("意见反馈成功，等待管理员处理！")
                    finish()
                } else {
                    toast(e?.message!!)
                }
            }
        })
    }
}