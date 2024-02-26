package com.vincent.android.architecture.base.component.h5

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.vincent.android.architecture.base.BR
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.ActivityH5Binding
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.model.ToolbarModel

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：通用WebView页面
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Common.A_H5)
class H5Activity : BaseToolbarActivity<ActivityH5Binding, BaseViewModel>() {

    @JvmField
    @Autowired(name = "title")
    var title: String? = null

    @JvmField
    @Autowired(name = "url")
    var url: String? = null

    @JvmField
    @Autowired(name = "isHtml")
    var isHtml: Boolean? = false

    @JvmField
    @Autowired(name = "htmlContent")
    var htmlContent: String? = null

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_h5
    }

    override fun initVariableId(): Int {
        return BR.h5VM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = title ?: "")
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        KeyboardUtils.fixAndroidBug5497(this)

        binding.webView.settings.apply {
            //支持javascript
            javaScriptEnabled = true
            // 设置可以支持缩放
            setSupportZoom(true)
            // 设置出现缩放工具
            builtInZoomControls = true
            //自适应屏幕
            if (!isHtml!!) {
                //扩大比例的缩放
                useWideViewPort = true
                //自适应屏幕
                loadWithOverviewMode = true
            }
            displayZoomControls = false
        }

        binding.webView.webViewClient = webViewClient

        binding.page.onRefresh {
            if (isHtml!!) {
                binding.webView.loadDataWithBaseURL(
                    null, htmlContent!!,
                    "text/html", "utf-8", null
                )
            } else {
                if (RegexUtils.isURL(url)) {
                    binding.webView.loadUrl(url!!)
                } else {
                    binding.page.showError(string(R.string.common_illegal_link))
                }
            }
        }.showLoading()
    }

    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.page.showContent()
        }

        // 链接跳转都会走这个方法
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url) // 强制在当前 WebView 中加载 url
            return true
        }
    }

    /**
     * 如果启用了JavaScript，务必做好安全措施，防止远程执行漏洞
     *
     * @param webView
     */
    @TargetApi(11)
    private fun removeJavascriptInterfaces(webView: WebView) {
        try {
            webView.removeJavascriptInterface("searchBoxJavaBridge_")
            webView.removeJavascriptInterface("accessibility")
            webView.removeJavascriptInterface("accessibilityTraversal")
        } catch (tr: Throwable) {
            tr.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放资源
        removeJavascriptInterfaces(binding.webView)
        binding.webView.destroy()
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}