package com.vincent.android.architecture.main.dining_hall.home

import android.app.Application
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.extention.startARouterActivity

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {
    val dishClick = BindingClick {
        startARouterActivity(C.RouterPath.DiningHall.A_DISH)
    }
    val deliverClick = BindingClick {
        startARouterActivity(C.RouterPath.DiningHall.A_DELIVER)
    }
    val statisticsClick = BindingClick {
        startARouterActivity(C.RouterPath.DiningHall.A_STATISTICS)
    }
    val feedbackClick = BindingClick {
        startARouterActivity(C.RouterPath.DiningHall.A_DH_FEEDBACK)
    }
}