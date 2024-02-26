package com.vincent.android.architecture.base.core

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.ObjectUtils
import com.drake.channel.receiveTag
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.extention.loginOrNot
import com.vincent.android.architecture.base.extention.observe
import com.vincent.android.architecture.base.widget.dialog.LoadingDialog
import java.lang.reflect.ParameterizedType

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/28
 * 描    述：Fragment基类
 * 修订历史：
 * ================================================
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> :
    LifecycleFragment(), IBaseView, SimpleImmersionOwner {

    lateinit var binding: V
    lateinit var viewModel: VM
    private var viewModelId = 0

    //当前界面是否可见
    var isVisibleToUser = false
    var isInitDate = false

    //ImmersionBar代理类
    private val mSimpleImmersionProxy by lazy { SimpleImmersionProxy(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleImmersionProxy.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            initContentView(inflater, container, savedInstanceState),
            container,
            false
        )
        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!immersionBarEnable) return
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (!immersionBarEnable) return
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */

    abstract val immersionBarEnable: Boolean

    override fun immersionBarEnabled(): Boolean {
        return immersionBarEnable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //解除ViewModel生命周期感应
        lifecycle.removeObserver(viewModel)
        binding.unbind()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding()
        //私有的ViewModel与View的契约事件回调逻辑
        registerVMLiveDataObserver()
        //init
        initView()
        initObservable()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!immersionBarEnable) return
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!immersionBarEnable) return
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    // 注册ViewModel与View的契约UI回调事件
    private fun registerVMLiveDataObserver() {
        //关闭界面
        observe(viewModel.finishObserver) { activity?.finish() }

        //加载对话框显示
        observe(viewModel.loadingObserver) { loading() }

        //加载对话框消失
        observe(viewModel.hideLoadingObserver) { hideLoading() }

        //登录状态监听
        receiveTag(C.BusTAG.LOGIN_STATUE) {
            onLoginObservable(loginOrNot())
        }
    }

    //-------------------------initViewDataBinding-------------------------
    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        viewModelId = initVariableId()
        viewModel = initViewModel()
        binding.setVariable(viewModelId, viewModel)
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this)
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    private fun initViewModel(): VM {
        val modelClass: Class<*>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        return createViewModel<ViewModel>(this, modelClass) as VM
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel?> createViewModel(fragment: Fragment?, cls: Class<*>?): T {
        return ViewModelProviders.of(fragment!!).get(cls as Class<T>)
    }

    //-------------------------initViewDataBinding-------------------------

    //-------------------------LoadingDialog-------------------------
    private var loadingDialog: LoadingDialog? = null

    open fun loading() {
        if (!ObjectUtils.isEmpty(loadingDialog) && loadingDialog!!.isShow) return
        if (ObjectUtils.isEmpty(loadingDialog)) {
            loadingDialog = context?.let { LoadingDialog(it) }
        }
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
            .dismissOnTouchOutside(false)
            .asCustom(loadingDialog)
            .show()
    }

    open fun hideLoading() {
        if (!ObjectUtils.isEmpty(loadingDialog) && loadingDialog!!.isShow)
            loadingDialog?.smartDismiss()
    }
    //-------------------------LoadingDialog-------------------------

    override fun initImmersionBar() {
    }

    override fun initData() {}
    override fun initStatusBar() {}
    override fun initView() {}
    override fun initObservable() {}
    override fun onLoginObservable(isLogin: Boolean) {}
    override fun onVisible(firstVisible: Boolean) {
        super.onVisible(firstVisible)
        isVisibleToUser = true
        if (firstVisible) {
            isInitDate = true
            initData()
        }
    }

    override fun onInvisible() {
        super.onInvisible()
        isVisibleToUser = false
    }
}
