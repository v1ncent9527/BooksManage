package com.vincent.android.architecture.main

import android.os.Bundle
import com.drake.net.utils.scope
import com.gyf.immersionbar.ImmersionBar
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.color
import com.vincent.android.architecture.base.extention.qrcode
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.main.databinding.MainActivitySplashBinding
import kotlinx.coroutines.delay

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/28
 * 描    述：启动页
 * 修订历史：
 * ================================================
 */
class SplashActivity : BaseActivity<MainActivitySplashBinding, BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.main_activity_splash
    }

    override fun initVariableId(): Int {
        return BR.splashVM
    }

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .fullScreen(true)
            .navigationBarColor(R.color.colorPrimary)
            .init()
    }

    override fun initView() {
        scope {
            delay(3000)
            startARouterActivity(C.RouterPath.Main.A_LOGIN)
            finish()
        }
    }
}