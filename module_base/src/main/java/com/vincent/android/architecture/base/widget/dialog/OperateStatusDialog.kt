package com.vincent.android.architecture.base.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.drake.net.utils.scope
import com.lxj.xpopup.core.CenterPopupView
import com.vincent.android.architecture.base.BR
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.databinding.DialogOperateStatusBinding
import com.vincent.android.architecture.base.extention.drawable
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.widget.dialog.vm.BaseDialogViewModel
import kotlinx.coroutines.delay

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：状态对话框
 * 修订历史：
 * ================================================
 */
@SuppressLint("ViewConstructor")
internal class OperateStatusDialog(
    context: Context,
    private val title: String = string(R.string.common_operate_success),
    private val statusImg: Drawable = drawable(R.drawable.svg_operate_success),
) : CenterPopupView(context) {
    private lateinit var binding: DialogOperateStatusBinding
    private lateinit var viewModel: BaseDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_operate_status
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = BaseDialogViewModel(Utils.getApp())
        binding.setVariable(BR.dialogVM, viewModel)

        initView()
    }

    private fun initView() {
        viewModel.title.set(title)
        binding.imgStatus.setImageDrawable(statusImg)

        scope {
            delay(1000)
            dismiss()
        }
    }
}