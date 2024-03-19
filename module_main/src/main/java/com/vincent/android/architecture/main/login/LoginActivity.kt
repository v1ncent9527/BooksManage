package com.vincent.android.architecture.main.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.LoginActivityBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：登录
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Main.A_LOGIN)
class LoginActivity :BaseToolbarActivity<LoginActivityBinding,LoginViewModel>(){
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.login_activity
    }

    override fun initVariableId(): Int {
        return BR.loginVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel()
    }

    override fun initView() {
        binding.cb.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) viewModel.type = 1 else viewModel.type = 0
        }
    }
}