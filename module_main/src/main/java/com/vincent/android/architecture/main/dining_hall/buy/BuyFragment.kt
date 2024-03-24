package com.vincent.android.architecture.main.dining_hall.buy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.alibaba.android.arouter.launcher.ARouter
import com.angcyo.tablayout.delegate.ViewPager1Delegate
import com.drake.channel.sendEvent
import com.lxj.xpopup.XPopup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.extention.add
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.mul
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.widget.VpAdapter
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentBuyBinding
import com.vincent.android.architecture.main.dining_hall.buy.item.BuyItemFragment
import com.vincent.android.architecture.main.dining_hall.dialog.BuyOrderDialog
import com.vincent.android.architecture.main.dining_hall.model.BuyOrderModel
import com.vincent.android.architecture.main.dining_hall.model.DishModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：点餐
 * 修订历史：
 * ================================================
 */
class BuyFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<FragmentBuyBinding, BuyViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_buy
    }

    override fun initVariableId(): Int {
        return BR.buyVM
    }

    override fun initView() {
        binding.vp.adapter = VpAdapter(
            fragmentManager = childFragmentManager,
            fragmentList = mutableListOf(
                BuyItemFragment(type = 0) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 1) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 2) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 3) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 4) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 5) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 6) { type, updateList ->
                    updatePrice(type, updateList)
                },
                BuyItemFragment(type = 7) { type, updateList ->
                    updatePrice(type, updateList)
                },
            )
        )
        ViewPager1Delegate.install(binding.vp, binding.dslTabLayout)

        binding.tvOrder.click {
            XPopup.Builder(requireContext()).asCustom(
                BuyOrderDialog(
                    context = requireContext()
                ) { tableNo, remark ->
                    val list: MutableList<DishModel> = mutableListOf()
                    for (priceUpdate in priceUpdateList) {
                        list.addAll(priceUpdate.list)
                    }
                    val buyOrderModel = BuyOrderModel(
                        System.currentTimeMillis(),
                        userModel!!.id,
                        userModel!!.username,
                        price,
                        1,
                        tableNo = tableNo,
                        remark = remark,
                        date = System.currentTimeMillis(),
                        orderList = list
                    )
                    buyOrderModel.save(object : SaveListener<String>() {
                        override fun done(objectId: String?, e: BmobException?) {
                            hideLoading()
                            if (!objectId.isNullOrEmpty()) {
                                toast("下单成功！")
                                price = 0.0
                                viewModel.totalPrice.set("¥$price")
                                viewModel.enablePrice.set(price)
                                sendEvent("order success", C.BusTAG.ORDER_SUCCESS)
                                ARouter.getInstance().build(C.RouterPath.DiningHall.A_BUY_ORDER)
                                    .withString("objectId", objectId)
                                    .navigation()
                            } else {
                                toast(e?.message!!)
                            }
                        }
                    })
                }).show()
        }
    }

    data class PriceUpdate(
        val type: Int,
        var list: List<DishModel>,
    )

    private val priceUpdateList = mutableListOf(
        PriceUpdate(0, listOf()),
        PriceUpdate(1, listOf()),
        PriceUpdate(2, listOf()),
        PriceUpdate(3, listOf()),
        PriceUpdate(4, listOf()),
        PriceUpdate(5, listOf()),
        PriceUpdate(6, listOf()),
        PriceUpdate(7, listOf()),
    )

    private var price = 0.0
    private fun updatePrice(type: Int, updateList: List<DishModel>) {
        price = 0.0
        for (item in priceUpdateList) {
            if (type == item.type)
                item.list = updateList
        }

        for (item in priceUpdateList) {
            for (dishModel in item.list) {
                price = add(price, mul(dishModel.price, dishModel.amount.toDouble()))
            }
        }
        viewModel.totalPrice.set("¥$price")
        viewModel.enablePrice.set(price)
    }

}