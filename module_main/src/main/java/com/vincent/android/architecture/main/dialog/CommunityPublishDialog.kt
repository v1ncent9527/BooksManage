package com.vincent.android.architecture.main.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.core.BottomPopupView
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.DialogCommunityPublishBinding
import com.vincent.android.architecture.main.model.BookModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
@SuppressLint("ViewConstructor")
class CommunityPublishDialog(
    context: Context,
    private val bookModel: BookModel,
    private var onConfirmListener: ((content: String, score: Double) -> Unit)
) : BottomPopupView(context) {
    private val c = context
    private lateinit var binding: DialogCommunityPublishBinding
    private lateinit var viewModel: MainDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_community_publish
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = MainDialogViewModel(Utils.getApp())
        binding.setVariable(BR.mainDialogVM, viewModel)

        initView()
    }

    private fun initView() {

        binding.bookModel = bookModel
        binding.btnConfirm.click {
            if (viewModel.todoContent.get().isEmpty()) {
                toast("请输入评论")
                return@click
            }
            onConfirmListener.invoke(viewModel.todoContent.get(), binding.rs.values[0].toDouble())
            dismiss()
        }

        binding.rs.setValues(bookModel.score.toFloat())
    }
}