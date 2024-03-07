package com.vincent.android.architecture.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate.ViewPager1Delegate
import com.gyf.immersionbar.ImmersionBar
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

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .titleBar(R.id.main_title)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.white)
            .init()
    }

    override fun initView() {
        val titleList = listOf("首页", "归还/续租", "书评圈", "好书推荐", "我的")

        binding.mainTitle.text = titleList[0]

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

        binding.dslTabLayout.configTabLayoutConfig {
            onSelectItemView = { _, index, select, _ ->
                if (select) {
                    binding.mainTitle.text = titleList[index]
                }
                false
            }
        }

//        //生成座位
//        (1..100).forEach {
//            val seatModel = SeatModel(
//                id = System.currentTimeMillis(),
//                no = it,
//                type = 0,
//                region = 3,
//            )
//            seatModel.save(object : SaveListener<String>() {
//                override fun done(objectId: String?, e: BmobException?) {
//                    objectId.logI("objectId")
//                }
//            })
//        }

    }
}