package com.vincent.android.architecture.main.dining_hall.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.core.BottomPopupView
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.gone
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.visible
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.DialogBuyOrderBinding

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
class BuyOrderDialog(
    context: Context,
    private var onConfirmListener: ((type: Int, address: String, remark: String) -> Unit)
) : BottomPopupView(context) {
    private val c = context
    private lateinit var binding: DialogBuyOrderBinding
    private lateinit var viewModel: DhDialogViewModel

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_buy_order
    }

    override fun onCreate() {
        binding = DataBindingUtil.bind(popupImplView)!!
        viewModel = DhDialogViewModel(Utils.getApp())
        binding.setVariable(BR.dhDialogVM, viewModel)

        initView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        var type = 0 //0 - 堂食  1 - 配送
        binding.cb1.setOnCheckedChangeListener { _, isCheck ->
            binding.cb2.isChecked = !isCheck
            type = if (isCheck) {
                0
            } else {
                1
            }
        }

        binding.cb2.setOnCheckedChangeListener { _, isCheck ->
            binding.cb1.isChecked = !isCheck
            type = if (isCheck) {
                1
            } else {
                0
            }
            if (isCheck) {
                binding.llAddress.visible()
            } else {
                binding.llAddress.gone()
            }
        }

        binding.btnConfirm.click {
            if (type == 1 && viewModel.orderAddress.get().isEmpty()) {
                toast("请输入送餐地址！")
                return@click
            }
            onConfirmListener.invoke(
                type,
                viewModel.orderAddress.get(),
                viewModel.orderRemark.get()
            )
            dismiss()
        }
    }
}