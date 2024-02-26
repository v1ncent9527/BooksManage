package com.vincent.android.architecture.base.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.core.CenterPopupView
import com.vincent.android.architecture.base.BR
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.databinding.DialogConfirmBinding
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.widget.dialog.vm.BaseDialogViewModel

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：确认对话框
 * 修订历史：
 * ================================================
 */
@SuppressLint("ViewConstructor")
internal class ConfirmDialog(
    context: Context,
    private val title: String = string(R.string.common_tips),
    private val content: String,
    private var onConfirmListener: (() -> Unit)? = null
) : CenterPopupView(context) {
    private val c = context
    private lateinit var binding: DialogConfirmBinding
    private lateinit var viewModel: BaseDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_confirm
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = BaseDialogViewModel(Utils.getApp())
        binding.setVariable(BR.dialogVM, viewModel)

        initViewObservable()
        initView()
    }

    private fun initView() {
        viewModel.title.set(title)
        viewModel.content.set(content)
    }

    private fun initViewObservable() {
        viewModel.clickObserver.observe(c as LifecycleOwner) {
            when (it) {
                "cancelClick" -> dismiss()
                "ensureClick" -> {
                    onConfirmListener?.invoke()
                    dismiss()
                }
            }
        }
    }
}