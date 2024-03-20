package com.vincent.android.architecture.main.dining_hall.my

import android.app.Application
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.extention.userModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
class MyViewModel(application: Application) : BaseViewModel(application) {
    val name = StringObservableField(userModel!!.nickname)

    //意见反馈 点击
    val dhFeedbackClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FEEDBACK)
    }
}