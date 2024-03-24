package com.vincent.android.architecture.main.dining_hall.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.angcyo.tablayout.delegate.ViewPager1Delegate
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.widget.VpAdapter
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentOrderBinding
import com.vincent.android.architecture.main.dining_hall.order.item.OrderItemFragment

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/22
 * 描    述：订单
 * 修订历史：
 * ================================================
 */
class OrderFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<FragmentOrderBinding, BaseViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_order
    }

    override fun initVariableId(): Int {
        return BR.orderVM
    }

    override fun initView() {
        binding.vp.adapter = VpAdapter(
            fragmentManager = childFragmentManager,
            fragmentList = mutableListOf(
                OrderItemFragment(statue = 0),
                OrderItemFragment(statue = 1),
                OrderItemFragment(statue = 2),
                OrderItemFragment(statue = 3),
                OrderItemFragment(statue = 4),
            )
        )
        ViewPager1Delegate.install(binding.vp, binding.dslTabLayout)
    }
}