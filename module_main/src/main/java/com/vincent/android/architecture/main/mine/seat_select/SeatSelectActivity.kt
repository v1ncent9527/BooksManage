package com.vincent.android.architecture.main.mine.seat_select

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityFocusBinding
import com.vincent.android.architecture.main.databinding.ActivitySeatSelectBinding

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
}