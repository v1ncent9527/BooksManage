package com.vincent.android.architecture.main.dining_hall.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.extention.visible
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentHomeBinding
import com.vincent.android.architecture.main.dining_hall.home.adapter.DhBannerImageAdapter
import com.vincent.android.architecture.main.dining_hall.model.NutritionModel
import com.youth.banner.indicator.RectangleIndicator

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
        } else {
            binding.prl.visible()
        }

        binding.banner
            .setBannerGalleryEffect(8, 14)
            .setAdapter(DhBannerImageAdapter(mutableListOf()))
            .setIndicator(RectangleIndicator(requireContext()))
            .addBannerLifecycleObserver(this)
            .setOnBannerListener { data, _ ->
                (data as NutritionModel).apply {
                    ARouter.getInstance().build(C.RouterPath.DiningHall.A_NUTRITION_DETAILS)
                        .withParcelable("nutritionModel", data)
                        .navigation()
                }
            }

        binding.rv.grid(2).divider {
            setDivider(14, true)
            orientation = DividerOrientation.GRID
            includeVisible = true
        }.setup {
            addType<NutritionModel> {
                R.layout.rv_item_nutritionmodel
            }
            onClick(R.id.rv_item) {
                ARouter.getInstance().build(C.RouterPath.DiningHall.A_NUTRITION_DETAILS)
                    .withParcelable("nutritionModel", getModel())
                    .navigation()
            }
        }

        binding.prl.onRefresh {
            BmobQuery<NutritionModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<NutritionModel?>() {
                    override fun done(list: List<NutritionModel?>?, e: BmobException?) {
                        list?.let {
                            binding.banner.setDatas(it.shuffled().take(3))
                            binding.rv.models = it
                            showContent()
                        }
                        if (!ObjectUtils.isEmpty(e)) {
                            showError(e?.message)
                        }
                    }
                })
        }
    }

    override fun initData() {
        binding.prl.showLoading()
    }
}