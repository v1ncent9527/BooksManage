package com.vincent.android.architecture.main.mine.focus_record

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.longClick
import com.vincent.android.architecture.base.extention.observe
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityFocusBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：专注时间
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Mine.A_FOCUS)
class FocusActivity : BaseToolbarActivity<ActivityFocusBinding, FocusViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_focus
    }

    override fun initVariableId(): Int {
        return BR.focusVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "专注时间")
    }

    override fun initView() {
        binding.llTime.longClick {
            confirmDialog(context = this, content = "确认结束此次专注?") {
                viewModel.resetTime()
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            viewModel.resetTime()
        }
    }

    override fun initObservable() {
        observe(viewModel.type) {
            operateBottomDialog(this, viewModel.typeList) {
                viewModel.typeSelect = viewModel.typeList[0]
                viewModel.isStart.set(true)
                viewModel.switchState(true)
                viewModel.startTime()
            }
        }
    }
}