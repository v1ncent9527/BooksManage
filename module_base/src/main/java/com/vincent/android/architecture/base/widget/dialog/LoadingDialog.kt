package com.vincent.android.architecture.base.widget.dialog

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.core.CenterPopupView
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.BR
import com.vincent.android.architecture.base.databinding.DialogLoadingBinding
import com.vincent.android.architecture.base.widget.dialog.vm.BaseDialogViewModel

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2021/10/28
 * 描    述：加载对话框
 * 修订历史：
 * ================================================
 */
internal class LoadingDialog(context: Context) : CenterPopupView(context) {
    private lateinit var binding: DialogLoadingBinding
    private lateinit var viewModel: BaseDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = BaseDialogViewModel(Utils.getApp())
        binding.setVariable(BR.dialogVM, viewModel)
    }
}