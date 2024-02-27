package com.vincent.android.architecture.main.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.CommunityFragmentBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：书评圈
 * 修订历史：
 * ================================================
 */
class CommunityFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<CommunityFragmentBinding, CommunityViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.community_fragment
    }

    override fun initVariableId(): Int {
        return BR.communityVM
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDivider(16, true)
            includeVisible = true
        }.setup {
            addType<String> { R.layout.rv_item_community }
        }._data = mutableListOf("","","","")
    }
}