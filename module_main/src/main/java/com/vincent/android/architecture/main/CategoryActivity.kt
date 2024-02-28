package com.vincent.android.architecture.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.main.databinding.CategoryActivityBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Main.A_CATEGORY)
class CategoryActivity:BaseActivity<CategoryActivityBinding,BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.category_activity
    }

    override fun initVariableId(): Int {
        return BR.categoryVM
    }

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .fullScreen(true)
            .navigationBarColor(R.color.white)
            .init()
    }

    override fun initView() {
        binding.llLibrary.click {
            startARouterActivity(C.RouterPath.Main.A_LOGIN)
            finish()
        }
    }
}