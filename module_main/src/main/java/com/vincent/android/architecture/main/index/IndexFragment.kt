package com.vincent.android.architecture.main.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.IndexFragmentBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：主页
 * 修订历史：
 * ================================================
 */
class IndexFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<IndexFragmentBinding, IndexViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.index_fragment
    }

    override fun initVariableId(): Int {
        return BR.indexVM
    }
}