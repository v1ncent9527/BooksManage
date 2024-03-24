package com.vincent.android.architecture.main.dining_hall.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import com.lxj.xpopup.core.BottomPopupView
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.DialogBuyOrderBinding
import com.vincent.android.architecture.main.dining_hall.model.TableModel

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
    private var onConfirmListener: ((tableNo: Int, remark: String) -> Unit)
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
        var tableNO = 0
        binding.rv.grid(2)
            .divider {
                setDivider(14, true)
                orientation = DividerOrientation.GRID
                includeVisible = true
            }.setup {
                addType<TableModel> { R.layout.rv_item_table_no }

                onClick(R.id.rv_item) {
                    if (tableNO != 0) {
                        (models?.get(tableNO - 1) as TableModel).isSelected = false
                    }
                    getModel<TableModel>().isSelected = true
                    tableNO = getModel<TableModel>().no
                    notifyDataSetChanged()
                }
            }.models = mutableListOf(
            TableModel(1, false),
            TableModel(2, false),
            TableModel(3, false),
            TableModel(4, false),
            TableModel(5, false),
            TableModel(6, false),
            TableModel(7, false),
            TableModel(8, false),
        )
        binding.btnConfirm.click {
            if (tableNO == 0) {
                toast("请先选择桌号！")
                return@click
            }
            onConfirmListener.invoke(tableNO, viewModel.orderRemark.get())
            dismiss()
        }
    }
}