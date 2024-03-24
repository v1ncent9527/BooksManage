package com.vincent.android.architecture.main.dining_hall.buy

import android.app.Application
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.DoubleObservableField
import com.vincent.android.architecture.base.databinding.StringObservableField
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
class BuyViewModel(application: Application) : BaseViewModel(application) {
    val totalPrice = StringObservableField("¥0")
    val enablePrice = DoubleObservableField(0.0)

    val checkClick = BindingClick {
        startARouterActivity(C.RouterPath.DiningHall.A_BUY_ORDER)
    }
}