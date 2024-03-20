package com.vincent.android.architecture.main.dining_hall.home.admin.feedback

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDhFeedbackBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：反馈收集
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_DH_FEEDBACK)
class DhFeedbackActivity : BaseToolbarActivity<ActivityDhFeedbackBinding, DhFeedbackViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_dh_feedback
    }

    override fun initVariableId(): Int {
        return BR.dhFeedbackVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "反馈收集")
    }
}