package com.vincent.android.architecture.main.dining_hall.home.admin.deliver

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDeliverBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：送餐管理
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_DELIVER)
class DeliverActivity : BaseToolbarActivity<ActivityDeliverBinding, DeliverViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_deliver
    }

    override fun initVariableId(): Int {
        return BR.deliverVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "送餐管理")
    }
}