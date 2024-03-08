package com.vincent.android.architecture.main.mine.focus_record

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.longClick
import com.vincent.android.architecture.base.extention.observe
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityFocusBinding
import com.vincent.android.architecture.main.model.FocusRecordModel

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
            if (viewModel.time.get() == "00:00:00") {
                false
            } else {
                confirmDialog(context = this, content = "确认结束此次专注?") {
                    loading()
                    val focusRecordModel = FocusRecordModel(
                        System.currentTimeMillis(),
                        userModel!!.id,
                        viewModel.typeSelect,
                        viewModel.time.get(),
                        System.currentTimeMillis(),
                    )
                    focusRecordModel.save(object : SaveListener<String>() {
                        override fun done(objectId: String?, e: BmobException?) {
                            hideLoading()
                            if (!objectId.isNullOrEmpty()) {
                                binding.prl.showLoading()
                            } else {
                                toast(e?.message!!)
                            }
                        }
                    })

                    viewModel.resetTime()
                }
                true
            }
        }

        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<FocusRecordModel> {
                R.layout.rv_item_focus_record
            }
        }

        binding.prl.onRefresh {
            BmobQuery<FocusRecordModel>().addWhereEqualTo("userId", userModel!!.id)
                .order("-updatedAt")
                .setLimit(10)
                .findObjects(object : FindListener<FocusRecordModel?>() {
                    override fun done(list: List<FocusRecordModel?>?, e: BmobException?) {
                        list?.let {
                            binding.rv.models = it
                            if (it.isEmpty()) showEmpty() else showContent()
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

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            viewModel.resetTime()
        }
    }

    override fun initObservable() {
        observe(viewModel.type) {
            operateBottomDialog(this, viewModel.typeList) {
                viewModel.typeSelect = viewModel.typeList[it]
                viewModel.isStart.set(true)
                viewModel.switchState(true)
                viewModel.startTime()
            }
        }
    }
}