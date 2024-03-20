package com.vincent.android.architecture.main.dining_hall.home.admin.dish

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDishBinding

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
    }
}