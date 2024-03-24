package com.vincent.android.architecture.main.dining_hall.buy.order

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBuyOrderBinding
import com.vincent.android.architecture.main.dining_hall.model.BuyOrderModel
import com.vincent.android.architecture.main.dining_hall.model.DishModel


/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/22
 * 描    述：订单详情
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_BUY_ORDER)
class BuyOrderActivity : BaseToolbarActivity<ActivityBuyOrderBinding, BaseViewModel>() {
    @JvmField
    @Autowired(name = "objectId")
    var objectId: String? = null

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_buy_order
    }

    override fun initVariableId(): Int {
        return BR.buyOrderVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "订单详情")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<DishModel> { R.layout.rv_item_buy_order_details }
        }

        binding.sl.onRefresh {
            BmobQuery<BuyOrderModel>().getObject(objectId, object : QueryListener<BuyOrderModel>() {
                override fun done(buyOrderModel: BuyOrderModel?, e: BmobException?) {
                    if (!ObjectUtils.isEmpty(buyOrderModel)) {
                        binding.buyOrderModel = buyOrderModel
                        binding.rv.models = buyOrderModel?.orderList
                        showContent()
                    } else {
                        showEmpty(e?.message)
                    }
                }
            })
        }
    }

    override fun initData() {
        binding.sl.showLoading()
    }
}