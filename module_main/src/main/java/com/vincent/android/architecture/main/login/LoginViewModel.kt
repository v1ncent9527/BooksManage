package com.vincent.android.architecture.main.login

import android.app.Application
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.IntObservableField
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.logI

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {
    val state = IntObservableField(0) //0 - 登录 1 - 注册
    val account = StringObservableField("")
    val pwd = StringObservableField("")

    val registerAccount = StringObservableField("")
    val registerNickname = StringObservableField("")
    val registerPwd = StringObservableField("")
    val registerPwdConfirm = StringObservableField("")

    val onStateClick = BindingClick {
        state.get().logI("22")
        if (state.get() == 0) {
            state.set(1)
            return@BindingClick
        }
        if (state.get() == 1) {
            state.set(0)
            return@BindingClick
        }
    }
    val onLoginClick = BindingClick {

    }

    val onRegisterClick = BindingClick {

    }
}