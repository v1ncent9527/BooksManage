package com.vincent.android.architecture.main.dialog

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.core.BottomPopupView
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.DialogTodoAddBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
class TodoAddDialog(
    context: Context,
    private var onConfirmListener: ((title: String, content: String) -> Unit)
) : BottomPopupView(context) {
    private val c = context
    private lateinit var binding: DialogTodoAddBinding
    private lateinit var viewModel: MainDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_todo_add
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = MainDialogViewModel(Utils.getApp())
        binding.setVariable(BR.mainDialogVM, viewModel)

        initView()
    }

    private fun initView() {
        binding.btnConfirm.click {
            if (viewModel.todoTitle.get().isEmpty()) {
                toast("请输入标题")
                return@click
            }
            if (viewModel.todoContent.get().isEmpty()) {
                toast("请输入内容")
                return@click
            }
            onConfirmListener.invoke(viewModel.todoTitle.get(), viewModel.todoContent.get())
            dismiss()
        }
    }
}