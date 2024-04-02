package com.vincent.android.architecture.main.dining_hall.home.nutrition

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityNutritionDetailsBinding
import com.vincent.android.architecture.main.dining_hall.model.NutritionModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/4/2
 * 描    述：营养详情
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_NUTRITION_DETAILS)
class NutritionDetailsActivity :
    BaseToolbarActivity<ActivityNutritionDetailsBinding, BaseViewModel>() {
    @JvmField
    @Autowired(name = "nutritionModel")
    var nutritionModel: NutritionModel? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_nutrition_details
    }

    override fun initVariableId(): Int {
        return BR.nutritionDetailsVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "${nutritionModel?.name} 营养详情")
    }

    override fun initView() {
        binding.nutritionModel = nutritionModel
    }
}