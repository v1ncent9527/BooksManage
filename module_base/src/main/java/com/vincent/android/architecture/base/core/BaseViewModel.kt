package com.vincent.android.architecture.base.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle2.LifecycleProvider
import com.vincent.android.architecture.base.databinding.LiveDataEvent
import java.lang.ref.WeakReference

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/28
 * 描    述：ViewModel基类
 * 修订历史：
 * ================================================
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {
    //弱引用持有
    private lateinit var lifecycle: WeakReference<LifecycleProvider<*>>

    //点击监听
    val clickObserver = LiveDataEvent<String>()

    //State
    val pageStateErrorObserver = LiveDataEvent<String>()

    //关闭界面
    val finishObserver = LiveDataEvent<Void>()

    //加载对话框
    val loadingObserver = LiveDataEvent<Void>()
    val hideLoadingObserver = LiveDataEvent<Void>()

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = WeakReference(lifecycle)
    }

    val lifecycleProvider: LifecycleProvider<*>?
        get() = lifecycle.get()

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {}
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}
    override fun onCleared() {}

    open fun finish() {
        finishObserver.call()
    }

    open fun loading() {
        loadingObserver.call()
    }

    open fun hideLoading() {
        hideLoadingObserver.call()
    }
}