package com.vincent.android.architecture.main.community

import android.app.Application
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
class CommunityViewModel(application: Application) : BaseViewModel(application) {

    //发布 点击
    val publishClick = BindingClick {
        startARouterActivity(C.RouterPath.Community.A_COMMUNITY_PUBLISH)
    }
}