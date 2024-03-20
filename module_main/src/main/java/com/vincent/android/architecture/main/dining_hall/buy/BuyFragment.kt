package com.vincent.android.architecture.main.dining_hall.buy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentBuyBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：点餐
 * 修订历史：
 * ================================================
 */
class BuyFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<FragmentBuyBinding, BuyViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_buy
    }

    override fun initVariableId(): Int {
        return BR.buyVM
    }
}