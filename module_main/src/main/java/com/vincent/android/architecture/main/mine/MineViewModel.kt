package com.vincent.android.architecture.main.mine

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
 * 创建日期：2024/2/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
class MineViewModel(application: Application) : BaseViewModel(application) {
    val name = StringObservableField(userModel!!.nickname)

    val todoClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_TODO)
    }
    val recordClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FOCUS)
    }
    val forumClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FORUM)
    }

}