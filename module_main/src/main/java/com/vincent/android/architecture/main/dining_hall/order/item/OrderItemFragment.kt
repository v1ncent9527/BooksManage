package com.vincent.android.architecture.main.dining_hall.order.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.receiveEvent
import com.drake.channel.sendEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentOrderItemBinding
import com.vincent.android.architecture.main.dining_hall.model.BuyOrderModel
import com.vincent.android.architecture.main.dining_hall.model.DishModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
class OrderItemFragment(override val immersionBarEnable: Boolean = false, val statue: Int) :
    BaseFragment<FragmentOrderItemBinding, BaseViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_order_item
    }

    override fun initVariableId(): Int {
        return BR.orderItemVM
    }

    override fun initView() {
        binding.rv.linear()
            .divider {
                setDrawable((R.drawable.shape_rv_divider_linear))
                endVisible = true
            }.setup {
                onCreate {
                    findView<RecyclerView>(R.id.rv_inner).linear(orientation = LinearLayout.HORIZONTAL)
                        .divider {
                            setDivider(14, true)
                            endVisible = true
                        }
                        .setup {
                            addType<DishModel> { R.layout.rv_item_buy_order_inner }
                        }
                }

                onBind {
                    findView<RecyclerView>(R.id.rv_inner).models =
                        getModel<BuyOrderModel>().orderList
                }

                addType<BuyOrderModel> { R.layout.rv_item_buy_order }

                onClick(R.id.rv_item) {
                    ARouter.getInstance().build(C.RouterPath.DiningHall.A_BUY_ORDER)
                        .withString("objectId", getModel<BuyOrderModel>().objectId)
                        .navigation()
                }

                onClick(R.id.tv_cancel) {
                    updateOrderStatue(4, getModel())
                }

                onClick(R.id.tv_arrive) {
                    updateOrderStatue(0, getModel())
                }
            }

        binding.prl.onRefresh {
            BmobQuery<BuyOrderModel>().addWhereEqualTo("userId", userModel!!.id)
                .addWhereEqualTo("statue", statue)
                .order("-updatedAt")
                .findObjects(object : FindListener<BuyOrderModel?>() {
                    override fun done(list: List<BuyOrderModel?>?, e: BmobException?) {
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

    private fun updateOrderStatue(statue: Int, model: BuyOrderModel) {
        confirmDialog(
            requireContext(),
            content = if (statue == 4) "是否取消订单?" else "是否确认已取餐？"
        ) {
            model.apply {
                val buyOrderModel = BuyOrderModel(
                    id,
                    userId,
                    userName,
                    price,
                    statue,
                    remark = remark,
                    date = date,
                    type = type,
                    address = address,
                    orderList = orderList
                )

                loading()
                buyOrderModel.update(objectId, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        hideLoading()
                        if (ObjectUtils.isEmpty(e)) {
                            toast(if (statue == 4) "取消订单成功！" else "操作成功！")
                            sendEvent("", C.BusTAG.ORDER_STATUE)
                        } else {
                            toast(e?.message!!)
                        }
                    }
                })
            }
        }
    }

    override fun initData() {
        binding.prl.showLoading()
    }

    override fun initObservable() {
        receiveEvent<String>(C.BusTAG.ORDER_STATUE) {
            binding.prl.showLoading()
        }

        receiveEvent<String>(C.BusTAG.ORDER_SUCCESS) {
            binding.prl.showLoading()
        }
    }
}