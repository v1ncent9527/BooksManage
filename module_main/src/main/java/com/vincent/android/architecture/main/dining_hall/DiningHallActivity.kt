package com.vincent.android.architecture.main.dining_hall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate.ViewPager1Delegate
import com.gyf.immersionbar.ImmersionBar
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.gone
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.widget.VpAdapter
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDiningHallBinding
import com.vincent.android.architecture.main.dining_hall.buy.BuyFragment
import com.vincent.android.architecture.main.dining_hall.home.HomeFragment
import com.vincent.android.architecture.main.dining_hall.my.MyFragment
import com.vincent.android.architecture.main.dining_hall.send.SendFragment

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：食堂主页面
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_MAIN)
class DiningHallActivity : BaseActivity<ActivityDiningHallBinding, BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_dining_hall
    }

    override fun initVariableId(): Int {
        return BR.diningHallVM
    }

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .titleBar(R.id.main_title)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.white)
            .init()
    }

    override fun initView() {
        val isAdmin = userModel!!.isAdmin()
        if (isAdmin) {
            binding.tvBuy.gone()
            binding.tvSend.gone()
        }
        val titleList = if (isAdmin) listOf(
            "首页",
            "我的"
        ) else listOf("首页", "点餐", "送餐", "我的")

        binding.mainTitle.text = titleList[0]
        binding.dslTabLayout.onPageSelected(0)

        binding.vp.adapter = VpAdapter(
            fragmentManager = supportFragmentManager,
            fragmentList = if (userModel!!.isAdmin()) mutableListOf(
                HomeFragment(),
                MyFragment(),
            ) else mutableListOf(
                HomeFragment(),
                BuyFragment(),
                SendFragment(),
                MyFragment(),
            )
        )
        ViewPager1Delegate.install(binding.vp, binding.dslTabLayout)

        binding.dslTabLayout.configTabLayoutConfig {
            onSelectItemView = { _, index, select, _ ->
                if (select) {
                    binding.mainTitle.text = titleList[index]
                }
                false
            }
        }
    }
}