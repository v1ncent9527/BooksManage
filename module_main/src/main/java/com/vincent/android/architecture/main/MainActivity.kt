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
import com.vincent.android.architecture.main.mine.MineFragment
import com.vincent.android.architecture.main.range.RangeFragment


/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：
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
        val titleList = listOf("首页", "书评圈", "好书推荐", "我的")

        binding.mainTitle.text = titleList[0]

        binding.vp.adapter = VpAdapter(
            fragmentManager = supportFragmentManager,
            fragmentList = mutableListOf(
                IndexFragment(),
//                LeaseFragment(),
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

//        val model = BookModel(
//            id = System.currentTimeMillis(),
//            name = "长安的荔枝",
//            author = "马伯庸",
//            score = 8.5,
//            summary = "大唐天宝十四年，长安城的小吏李善德突然接到一个任务：要在贵妃诞日之前，从岭南运来新鲜荔枝。荔枝“一日色变，两日香变，三日味变”，而岭南距长安五千余里，山水迢迢，这是个不可能完成的任务，可为了家人，李善德决心放手一搏：“就算失败，我也想知道，自己倒在距离终点多远的地方。”\n" +
//                    "\n" +
//                    "《长安的荔枝》是马伯庸备受好评的历史小说。\n" +
//                    "\n" +
//                    "唐朝诗人杜牧的一句“一骑红尘妃子笑，无人知是荔枝来”一千多年来引发了人们的无限遐想，但鲜荔枝的保鲜时限仅有三天，这场跨越五千余里的传奇转运之旅究竟是如何达成的，谁让杨贵妃在长安吃到了来自岭南的鲜荔枝？作者马伯庸就此展开了一场脑洞非常大的想象。\n" +
//                    "\n" +
//                    "沿袭马伯庸写作一贯以来的时空紧张感，不仅让读者看到了小人物的乱世生存之道，也感受到了事在人为的热血奋斗。随书附赠“荔枝鲜转运舆图”。",
//            publishingHouse = "湖南文艺出版社",
//            logoUrl = "https://img2.imgtp.com/2024/03/07/fcypqCmk.jpg",
//        )
//        model.save(object : SaveListener<String>() {
//            override fun done(objectId: String?, e: BmobException?) {
//                objectId.logI("objectId")
//            }
//        })

    }
}