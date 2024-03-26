package com.vincent.android.architecture.main.dining_hall.home.admin.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.roundDown
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityStatisticsBinding
import com.vincent.android.architecture.main.dining_hall.model.DishModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：财务统计
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_STATISTICS)
class StatisticsActivity : BaseToolbarActivity<ActivityStatisticsBinding, StatisticsViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_statistics
    }

    override fun initVariableId(): Int {
        return BR.statisticsVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "财务统计")
    }

    override fun initView() {
        binding.rv.linear()
            .divider {
                setDrawable((R.drawable.shape_rv_divider_linear))
                endVisible = true
            }
            .setup {
                addType<DishModel> { R.layout.rv_item_statistics }
            }

        binding.prl.onRefresh {
            BmobQuery<DishModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<DishModel?>() {
                    @SuppressLint("SetTextI18n")
                    override fun done(list: List<DishModel?>?, e: BmobException?) {
                        if (list.isNullOrEmpty()) {
                            showEmpty()
                        } else {
                            binding.rv.models = list
                            var profit = 0.0
                            list.forEach {
                                profit += (it as DishModel).bindProfitDouble()
                            }
                            binding.tvTotalProfit.text = "总利润：¥${profit.roundDown(1)}"
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