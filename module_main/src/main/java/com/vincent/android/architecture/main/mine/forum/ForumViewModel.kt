package com.vincent.android.architecture.main.mine.forum

import android.app.Application
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.extention.startARouterActivity

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
class ForumViewModel(application: Application) : BaseViewModel(application) {
    val sendClick = BindingClick {
        startARouterActivity(C.RouterPath.Mine.A_FORUM_PUBLISH)
    }
}