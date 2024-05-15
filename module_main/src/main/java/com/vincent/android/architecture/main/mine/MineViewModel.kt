package com.vincent.android.architecture.main.mine

import android.app.Application
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.BooleanObservableField
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.h5
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.extention.userModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
class MineViewModel(application: Application) : BaseViewModel(application) {
    val name = StringObservableField(userModel!!.nickname)
    val monitorVisible = BooleanObservableField(userModel!!.isAdmin())

    //任务清单 点击
    val todoClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_TODO)
    }

    //专注记录 点击
    val recordClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FOCUS)
    }

    //论坛 点击
    val forumClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FORUM)
    }

    //环境监测 点击
    val monitorClick = BindingClick {
        h5(
            "环境监测",
            "https://login.dfrobot.com/member/login?backUrl=https://iot.dfrobot.com/workshop.html"
        )
    }

    //意见反馈 点击
    val feedbackClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FEEDBACK)
    }

}