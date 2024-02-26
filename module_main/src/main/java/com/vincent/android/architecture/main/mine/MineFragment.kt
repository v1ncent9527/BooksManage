package com.vincent.android.architecture.main.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.MineFragmentBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：我的
 * 修订历史：
 * ================================================
 */
class MineFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<MineFragmentBinding, MineViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.mine_fragment
    }

    override fun initVariableId(): Int {
        return BR.mineVM
    }
}