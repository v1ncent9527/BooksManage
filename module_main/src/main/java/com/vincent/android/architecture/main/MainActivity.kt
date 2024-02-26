package com.vincent.android.architecture.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate.ViewPager1Delegate
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.widget.VpAdapter
import com.vincent.android.architecture.main.community.CommunityFragment
import com.vincent.android.architecture.main.databinding.MainActivityMainBinding
import com.vincent.android.architecture.main.index.IndexFragment
import com.vincent.android.architecture.main.lease.LeaseFragment
import com.vincent.android.architecture.main.mine.MineFragment
import com.vincent.android.architecture.main.range.RangeFragment

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/5/1
 * 描    述：主页面
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Main.A_MAIN)
class MainActivity : BaseActivity<MainActivityMainBinding, BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.main_activity_main
    }

    override fun initVariableId(): Int {
        return BR.mainVM
    }

    override fun initView() {
        binding.vp.adapter = VpAdapter(
            fragmentManager = supportFragmentManager,
            fragmentList = mutableListOf(
                IndexFragment(),
                LeaseFragment(),
                CommunityFragment(),
                RangeFragment(),
                MineFragment(),
            )
        )
        ViewPager1Delegate.install(binding.vp, binding.dslTabLayout)
    }
}