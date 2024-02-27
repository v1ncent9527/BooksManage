package com.vincent.android.architecture.main.login

import android.app.Application
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.IntObservableField
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.logI
import com.vincent.android.architecture.base.extention.logJson
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.extention.toJson
import com.vincent.android.architecture.main.model.UserModel


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
        startARouterActivity(C.RouterPath.Main.A_MAIN)
    }

    val onRegisterClick = BindingClick {
        val user = UserModel(nickname = "v1ncent")
        user.username = "v1ncent"
        user.setPassword("v1ncent")
        user.signUp(object : SaveListener<UserModel>() {
            override fun done(user: UserModel?, e: BmobException?) {
                user.toJson().logJson("UserModel")
                e.toJson().logJson("BmobException")
//                if (e == null) {
//                    toast("注册成功")
//                } else {
//                    toast("注册失败")
//                }
            }
        })
    }
}