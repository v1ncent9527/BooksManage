package com.vincent.android.architecture.main.index

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.extention.startARouterActivity

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
class IndexViewModel(application: Application) : BaseViewModel(application) {

    //在借书籍 点击
    val booksIntroductionClick = BindingClick {
        ARouter.getInstance().build(C.RouterPath.Index.A_BOOK_READING)
            .withInt("type", 0)
            .navigation()
    }

    //历史书籍 点击
    val historyOrderClick = BindingClick {
        ARouter.getInstance().build(C.RouterPath.Index.A_BOOK_READING)
            .withInt("type", 1)
            .navigation()
    }

    //书城 点击
    val bookMarketClick = BindingClick {
        startARouterActivity(C.RouterPath.Index.A_BOOK_MARKET)
    }
}