package com.vincent.android.architecture.main.dining_hall.buy.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.receiveEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentBuyItemBinding
import com.vincent.android.architecture.main.dining_hall.model.DishModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
class BuyItemFragment(
    override val immersionBarEnable: Boolean = false,
    val type: Int,
    var onUpdateListener: ((type: Int, updateList: List<DishModel>) -> Unit)
) :
    BaseFragment<FragmentBuyItemBinding, BaseViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_buy_item
    }

    override fun initVariableId(): Int {
        return BR.buyItemVM
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<DishModel> { R.layout.rv_item_buy }

            onClick(R.id.img_minus) {
                getModel<DishModel>().amount -= 1
                notifyItemChanged(modelPosition)
                onUpdateListener(type, (models as List<DishModel>).filter { it.amount > 0 })
            }

            onClick(R.id.img_add) {
                getModel<DishModel>().amount += 1
                notifyItemChanged(modelPosition)
                onUpdateListener(type, (models as List<DishModel>).filter { it.amount > 0 })
            }
        }

        binding.sl.onRefresh {
            BmobQuery<DishModel>().addWhereEqualTo("type", type).order("-updatedAt")
                .findObjects(object : FindListener<DishModel?>() {
                    override fun done(list: List<DishModel?>?, e: BmobException?) {
                        if (list.isNullOrEmpty()) {
                            showEmpty()
                        } else {
                            binding.rv.models = list
                            showContent()
                        }

                        if (!ObjectUtils.isEmpty(e)) {
                            showError(e?.message)
                        }
                    }
                })
        }.showLoading()
    }

    override fun initData() {
        binding.sl.showLoading()
    }

    override fun initObservable() {
        receiveEvent<String>(C.BusTAG.ORDER_SUCCESS) {
            binding.sl.showLoading()
        }
    }
}