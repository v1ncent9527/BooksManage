package com.vincent.android.architecture.main.lease.book_market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentBookMarketBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/28
 * 描    述：书城
 * 修订历史：
 * ================================================
 */
class BookMarketFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<FragmentBookMarketBinding, BookMarketViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_book_market
    }

    override fun initVariableId(): Int {
        return BR.bookMarketVM
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<String> { R.layout.rv_item_book_market }
        }.models = mutableListOf("","","","")
    }
}