package com.vincent.android.architecture.main.mine.seat_select

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.toJson
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivitySeatSelectBinding
import com.vincent.android.architecture.main.model.SeatModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：在线选座
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Mine.A_SEAT_SELECT)
class SeatSelectActivity : BaseToolbarActivity<ActivitySeatSelectBinding, SeatSelectViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_seat_select
    }

    override fun initVariableId(): Int {
        return BR.seatSelectVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "在线选座")
    }

    override fun initView() {
        val list = mutableListOf<SeatModel>()
        (1..100).forEach {
            when (it) {
                in 1..30 -> {
                    list.add(SeatModel(no = it, type = 0))
                }

                in 31..100 -> {
                    list.add(SeatModel(no = it, type = 1))
                }
            }
        }
        binding.rv.grid(10)
            .divider {
                setDivider(14, true)
                orientation = DividerOrientation.GRID
            }.setup {
                addType<SeatModel> {
                    R.layout.rv_item_select_select
                }
                onClick(R.id.rv_item) {
                    if (getModel<SeatModel>().type == 1) return@onClick
                    (models as MutableList<SeatModel>).forEachIndexed { index, seatModel ->
                        if (index == modelPosition && seatModel.type == 0) {
                            seatModel.type = 2
                            notifyItemChanged(modelPosition)
                        }
                        if (index != modelPosition && seatModel.type == 2) {
                            seatModel.type = 0
                            notifyItemChanged(index)
                        }
                    }
                }
            }.models = list

        binding.btnConfirm.click {
            val model = (binding.rv.models as MutableList<SeatModel>).find { it.type == 2 }
            if (ObjectUtils.isEmpty(model)) {
                toast("请先选择座位！")
            } else {
                toast(model.toJson())
            }
        }
    }
}