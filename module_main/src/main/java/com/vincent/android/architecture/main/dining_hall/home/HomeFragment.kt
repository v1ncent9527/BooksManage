package com.vincent.android.architecture.main.dining_hall.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.extention.visible
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentHomeBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：首页
 * 修订历史：
 * ================================================
 */
class HomeFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.homeVM
    }

    override fun initView() {
        val isAdmin = userModel!!.isAdmin()

        if (isAdmin) {
            binding.llAdmin.visible()
        }
    }
}