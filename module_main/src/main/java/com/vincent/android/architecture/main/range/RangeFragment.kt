package com.vincent.android.architecture.main.range

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.RangeFragmentBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：好书推荐
 * 修订历史：
 * ================================================
 */
class RangeFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<RangeFragmentBinding, RangeViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.range_fragment
    }

    override fun initVariableId(): Int {
        return BR.rangeVM
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<String> { R.layout.rv_item_range }
        }._data = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    }
}