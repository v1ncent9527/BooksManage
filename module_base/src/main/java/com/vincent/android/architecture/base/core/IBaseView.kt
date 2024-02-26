package com.vincent.android.architecture.base.core

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
interface IBaseView {
    /**
     * 初始化界面
     */
    fun initView()

    /**
     * 初始化数据(Fragment具有懒加载功能)
     */
    fun initData()

    /**
     * 初始化StatusBar
     */
    fun initStatusBar()

    /**
     * 初始化Livedata监听
     */
    fun initObservable()

    /**
     * 初始化登录状态监听
     */
    fun onLoginObservable(isLogin: Boolean)

}