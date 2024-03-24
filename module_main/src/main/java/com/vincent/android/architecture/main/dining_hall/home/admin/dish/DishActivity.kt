package com.vincent.android.architecture.main.dining_hall.home.admin.dish

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.receiveEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDishBinding
import com.vincent.android.architecture.main.dining_hall.model.DishModel


/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：菜品管理
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_DISH)
class DishActivity : BaseToolbarActivity<ActivityDishBinding, BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_dish
    }

    override fun initVariableId(): Int {
        return BR.dishVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "菜品管理")
    }

    override fun initView() {
        binding.imgAdd.click {
            startARouterActivity(C.RouterPath.DiningHall.A_DISH_MANAGE)
        }

        binding.rv.linear()
            .divider {
                setDrawable((R.drawable.shape_rv_divider_linear))
                endVisible = true
            }
            .setup {
                addType<DishModel> { R.layout.rv_item_dish_manage }

                onClick(R.id.img_more) {
                    operateBottomDialog(this@DishActivity, listOf("编辑", "下架")) {
                        when (it) {
                            0 -> {
                                ARouter.getInstance().build(C.RouterPath.DiningHall.A_DISH_MANAGE)
                                    .withParcelable("dishModel", getModel())
                                    .withString("objectId", getModel<DishModel>().objectId)
                                    .navigation()
                            }

                            1 -> {
                                confirmDialog(
                                    context = this@DishActivity, content = "确认下架？"
                                ) {
                                    hideLoading()
                                    val dishModel = DishModel(
                                        System.currentTimeMillis(),
                                        "",
                                        "",
                                        0,
                                        "",
                                        "",
                                        0.0,
                                    )
                                    dishModel.objectId = getModel<DishModel>().objectId
                                    dishModel.delete(object : UpdateListener() {
                                        override fun done(e: BmobException?) {
                                            hideLoading()
                                            if (ObjectUtils.isEmpty(e)) {
                                                toast("下架成功！")
                                                initData()
                                            } else {
                                                toast(e?.message!!)
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }

        binding.prl.onRefresh {
            BmobQuery<DishModel>()
                .order("-updatedAt")
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
        }
    }

    override fun initData() {
        binding.prl.showLoading()
    }

    override fun initObservable() {
        receiveEvent<String>(C.BusTAG.DISH_UPDATE) {
            initData()
        }
    }
}