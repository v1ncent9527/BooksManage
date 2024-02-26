package com.vincent.android.architecture.base.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.Utils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.lxj.xpopup.core.BottomPopupView
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.BR
import com.vincent.android.architecture.base.databinding.DialogOperateBottomBinding
import com.vincent.android.architecture.base.widget.dialog.vm.BaseDialogViewModel

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：底部操作栏
 * 修订历史：
 * ================================================
 */
@SuppressLint("ViewConstructor")
internal class OperateBottomDialog(
    context: Context,
    val list: List<String>,
    private var onOperateBottomSelectListener: ((position: Int) -> Unit)
) : BottomPopupView(context) {
    private val c = context
    private lateinit var binding: DialogOperateBottomBinding
    private lateinit var viewModel: BaseDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_operate_bottom
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = BaseDialogViewModel(Utils.getApp())
        binding.setVariable(BR.dialogVM, viewModel)

        initViewObservable()
        initView()
    }

    private fun initView() {
        binding.rv.linear()
            .divider(R.drawable.shape_rv_divider_linear)
            .setup {
                addType<String> { R.layout.item_operate_bottom }
                R.id.rv_item.onClick {
                    onOperateBottomSelectListener.invoke(modelPosition)
                    dismiss()
                }
            }.models = list
    }

    private fun initViewObservable() {
        viewModel.clickObserver.observe(c as LifecycleOwner) {
            when (it) {
                "cancelClick" -> dismiss()
            }
        }
    }
}