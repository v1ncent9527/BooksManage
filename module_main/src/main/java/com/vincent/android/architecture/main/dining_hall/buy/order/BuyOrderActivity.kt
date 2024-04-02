package com.vincent.android.architecture.main.dining_hall.buy.order

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.sendEvent
import com.king.zxing.util.CodeUtils
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.gone
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.extention.visible
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBuyOrderBinding
import com.vincent.android.architecture.main.dining_hall.model.BuyOrderModel
import com.vincent.android.architecture.main.dining_hall.model.DhFeedBackModel
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

    var detailsModel: BuyOrderModel? = null
    var error: String = ""
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
                        detailsModel = buyOrderModel
                        binding.rv.models = buyOrderModel?.orderList
                        binding.qrCode.setImageBitmap(CodeUtils.createQRCode(objectId, 600))

                        showContent()
                    } else {
                        showEmpty(e?.message)
                    }
                }
            })
        }

        binding.tvCancel.click {
            updateOrderStatue(4)
        }

        binding.tvArrive.click {
            updateOrderStatue(0)
        }

        binding.tvSend.click {
            updateOrderStatue(1)
        }

        binding.tvPick.click {
            updateOrderStatue(0)
        }

        binding.cb.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                binding.llCb.visible()
                binding.tvErrorUpload.visible()
            } else {
                binding.llCb.gone()
                binding.tvErrorUpload.gone()
            }
        }

        binding.cbSub1.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                error = "汤羹泼洒"
                binding.cbSub2.isChecked = false
                binding.cbSub3.isChecked = false
            }
        }

        binding.cbSub2.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                error = "地点出错"
                binding.cbSub1.isChecked = false
                binding.cbSub3.isChecked = false
            }
        }
        binding.cbSub3.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                error = "其他原因"
                binding.cbSub1.isChecked = false
                binding.cbSub2.isChecked = false
            }
        }

        binding.tvErrorUpload.click {
            updateOrderStatue(3)
        }
    }

    override fun initData() {
        binding.sl.showLoading()
    }

    private fun updateOrderStatue(statue: Int) {
        confirmDialog(
            this,
            content = when (statue) {
                4 -> {
                    "是否取消订单?"
                }

                3 -> {
                    "是否配送出错?"
                }

                0 -> {
                    "是否已取餐？"
                }

                1 -> {
                    "是否开始配送？"
                }

                else -> {
                    ""
                }
            }
        ) {
            detailsModel?.apply {
                val buyOrderModel = BuyOrderModel(
                    id,
                    userId,
                    userName,
                    price,
                    statue,
                    errorMsg = error,
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
                            toast(
                                when (statue) {
                                    4 -> {
                                        "取消订单成功！"
                                    }

                                    3, 0 -> {
                                        "操作成功！"
                                    }

                                    1 -> {
                                        "订单已开始配送！"
                                    }

                                    else -> {
                                        ""
                                    }
                                }
                            )
                            sendEvent("", C.BusTAG.ORDER_STATUE)
                            binding.sl.showLoading()
                        } else {
                            toast(e?.message!!)
                        }

                        if (statue == 3) {
                            val feedBackModel = DhFeedBackModel(
                                System.currentTimeMillis(),
                                userModel!!.id,
                                userModel!!.nickname,
                                "配送出错",
                                content = error,
                                System.currentTimeMillis(),
                            )
                            feedBackModel.save(object : SaveListener<String>() {
                                override fun done(objectId: String?, e: BmobException?) {
                                }
                            })
                        }
                    }
                })
            }
        }
    }

}