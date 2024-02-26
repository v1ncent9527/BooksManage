package com.vincent.android.architecture.main.lease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.LeaseFragmentBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：租借/归还
 * 修订历史：
 * ================================================
 */
class LeaseFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<LeaseFragmentBinding, LeaseViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.lease_fragment
    }

    override fun initVariableId(): Int {
        return BR.leaseVM
    }
}