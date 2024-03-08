package com.vincent.android.architecture.main.login

import android.app.Application
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.blankj.utilcode.util.ObjectUtils
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.IntObservableField
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.model.UserModel


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
        if (account.get().isEmpty() || pwd.get().isEmpty()) {
            toast("请先完善信息后提交！")
            return@BindingClick
        }

        loading()
        val user = UserModel()
        user.username = account.get()
        user.setPassword(pwd.get())
        user.login(object : SaveListener<UserModel>() {
            override fun done(user: UserModel?, e: BmobException?) {
                hideLoading()
                if (!ObjectUtils.isEmpty(user)) {
                    toast("登录成功！")
                    userModel = user
                    startARouterActivity(C.RouterPath.Main.A_CATEGORY)
                    finish()
                } else {
                    if (!ObjectUtils.isEmpty(e)) {
                        toast(e?.message ?: "注册失败，未知错误！")
                    }
                }
            }
        })

    }

    val onRegisterClick = BindingClick {
        if (registerNickname.get().isEmpty() || registerAccount.get()
                .isEmpty() || registerPwd.get().isEmpty() || registerPwdConfirm.get().isEmpty()
        ) {
            toast("请先完善信息后提交！")
            return@BindingClick
        }

        if (registerPwd.get() != registerPwdConfirm.get()) {
            toast("两次密码输入不一致，请重试！")
            return@BindingClick
        }

        loading()
        val user = UserModel(id = System.currentTimeMillis(), nickname = registerNickname.get())
        user.username = registerAccount.get()
        user.setPassword(registerPwd.get())
        user.signUp(object : SaveListener<UserModel>() {
            override fun done(user: UserModel?, e: BmobException?) {
                hideLoading()
                if (!ObjectUtils.isEmpty(user)) {
                    toast("注册成功！")
                    state.set(0)
                } else {
                    if (!ObjectUtils.isEmpty(e)) {
                        toast(e?.message ?: "注册失败，未知错误！")
                    }
                }
            }
        })
    }
}