package com.vincent.android.architecture.base.core

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.channel.receiveTag
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.loginOrNot
import com.vincent.android.architecture.base.extention.observe
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.LoadingDialog
import java.lang.reflect.ParameterizedType

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/28
 * 描    述：Activity(带Toolbar)基类
 * 修订历史：
 * ================================================
 */
abstract class BaseToolbarActivity<V : ViewDataBinding, VM : BaseViewModel> :
    RxAppCompatActivity(), IBaseView {

    lateinit var binding: V
    lateinit var viewModel: VM
    private var viewModelId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ARouter register
        ARouter.getInstance().inject(this)
        //私有的初始化DataBinding和ViewModel方法
        initViewDataBinding(savedInstanceState)
        //私有的ViewModel与View的契约事件回调逻辑
        registerVMLiveDataObserver()
        //init
        initStatusBar()
        initView()
        initData()
        initObservable()
    }

    override fun onDestroy() {
        super.onDestroy()
        //解除ViewModel生命周期感应
        lifecycle.removeObserver(viewModel)
        binding.unbind()
    }

    // 注册ViewModel与View的契约UI回调事件
    private fun registerVMLiveDataObserver() {
        //关闭界面
        observe(viewModel.finishObserver) { finish() }

        //加载对话框显示
        observe(viewModel.loadingObserver) { loading() }

        //加载对话框消失
        observe(viewModel.hideLoadingObserver) { hideLoading() }

        //登录状态监听
        receiveTag(C.BusTAG.LOGIN_STATUE) {
            onLoginObservable(loginOrNot())
        }
    }

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .titleBar(R.id.toolbar)
            .statusBarColor(R.color.white)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.white)
            .init()
    }

    override fun initView() {}
    override fun initData() {}
    override fun initObservable() {}
    override fun onLoginObservable(isLogin: Boolean) {}

    //-------------------------LoadingDialog-------------------------
    private var loadingDialog: LoadingDialog? = null

    open fun loading() {
        if (!ObjectUtils.isEmpty(loadingDialog) && loadingDialog!!.isShow) return
        if (ObjectUtils.isEmpty(loadingDialog)) {
            loadingDialog = LoadingDialog(this)
        }
        XPopup.Builder(this)
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


    //-------------------------initViewDataBinding-------------------------
    /**
     * 构建一个Toolbar
     */
    private fun onCreateToolbar(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.layout_toolbar, container, false)
    }

    /**
     * 注入绑定
     *
     * @param savedInstanceState
     */
    lateinit var tvLeftText: TextView
    lateinit var ivLeftIcon: ImageView
    lateinit var tvTitleText: TextView
    lateinit var tvRightText: TextView
    lateinit var ivRightIcon: ImageView
    lateinit var tvRightButton: TextView
    lateinit var vDivider: View

    lateinit var toolBarModel: ToolbarModel

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        val contentView = layoutInflater.inflate(R.layout.activity_toolbar, null, false)
        setContentView(contentView)
        val container = contentView as ViewGroup
        val toolbar = onCreateToolbar(layoutInflater, container)
        DataBindingUtil.bind<ViewDataBinding>(toolbar)
        container.addView(toolbar)
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.inflate(
            layoutInflater,
            initContentView(savedInstanceState),
            container,
            true
        )

        //设置Toolbar
        tvLeftText = findViewById(R.id.tv_left_text)
        ivLeftIcon = findViewById(R.id.iv_left_icon)
        tvTitleText = findViewById(R.id.tv_title)
        tvRightText = findViewById(R.id.tv_right_text)
        ivRightIcon = findViewById(R.id.iv_right_icon)
        tvRightButton = findViewById(R.id.tv_right_button)
        vDivider = findViewById(R.id.v_divider)

        toolBarModel = initToolbar()
        toolBarModel.apply {
            tvLeftText.apply {
                text = leftText
                setTextColor(leftTextColor)
                visibility = leftTextVisible
                click {
                    toolBarLeftClick()
                }
            }
            ivLeftIcon.apply {
                setImageDrawable(leftIcon)
                visibility = leftIconVisible
                click {
                    toolBarLeftClick()
                }
            }
            tvTitleText.apply {
                text = titleText
                setTextColor(titleTextColor)
            }
            tvRightText.apply {
                text = rightText
                setTextColor(rightTextColor)
                visibility = rightTextVisible
                click {
                    toolBarRightClick()
                }
            }
            ivRightIcon.apply {
                setImageDrawable(rightIcon)
                visibility = rightIconVisible
                click {
                    toolBarRightClick()
                }
            }
            tvRightButton.apply {
                text = rightButton
                background = rightButtonDrawable
                visibility = rightButtonVisible
                click {
                    toolBarRightClick()
                }
            }
            vDivider.visibility = dividerVisible
        }

        viewModelId = initVariableId()
        viewModel = initViewModel()
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel)
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this)
    }

    open fun toolBarLeftClick() {
        finish()
    }

    open fun toolBarRightClick() {
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化
     */
    abstract fun initToolbar(): ToolbarModel

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
    private fun <T : ViewModel?> createViewModel(activity: FragmentActivity?, cls: Class<*>?): T {
        return ViewModelProviders.of(activity!!).get(cls as Class<T>)
    }

    //-------------------------initViewDataBinding-------------------------

    //-------------------------设置点击空白区域隐藏键盘-------------------------
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    //-------------------------设置点击空白区域隐藏键盘-------------------------

    //-------------------------字体大小不跟随系统-------------------------
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    //-------------------------字体大小不跟随系统-------------------------
}
