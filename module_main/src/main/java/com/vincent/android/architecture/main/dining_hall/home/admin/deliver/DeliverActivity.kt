package com.vincent.android.architecture.main.dining_hall.home.admin.deliver

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate.ViewPager1Delegate
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.VpAdapter
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDeliverBinding
import com.vincent.android.architecture.main.dining_hall.order.item.OrderItemAdminFragment

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

    override fun initView() {
        binding.vp.adapter = VpAdapter(
            fragmentManager = supportFragmentManager,
            fragmentList = mutableListOf(
                OrderItemAdminFragment(statue = 0),
                OrderItemAdminFragment(statue = 1),
                OrderItemAdminFragment(statue = 2),
                OrderItemAdminFragment(statue = 3),
                OrderItemAdminFragment(statue = 4),
            )
        )
        ViewPager1Delegate.install(binding.vp, binding.dslTabLayout)
    }
}