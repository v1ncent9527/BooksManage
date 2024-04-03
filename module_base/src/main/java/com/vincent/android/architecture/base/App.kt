package com.vincent.android.architecture.base

import ando.file.core.FileOperator
import android.util.Log
import android.widget.TextView
import cn.bmob.v3.Bmob
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.drake.brv.utils.BRV
import com.drake.net.NetConfig
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.interfaces.NetErrorHandler
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import com.drake.net.okhttp.setErrorHandler
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.net.okhttp.trustSSLCertificate
import com.drake.net.request.BaseRequest
import com.drake.statelayout.StateConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.vincent.android.architecture.base.extention.color
import com.vincent.android.architecture.base.extention.startStateAnimation
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.net.Host
import com.vincent.android.architecture.base.net.converter.GsonConverter
import com.vincent.android.architecture.base.net.cookie.CookieManger
import com.vincent.android.architecture.base.net.ext.code
import com.vincent.android.architecture.base.net.ext.throwToast
import com.vincent.android.architecture.base.utils.MMKVUtils
import com.vincent.android.architecture.base.widget.MaterialHeader
import org.litepal.LitePalApplication
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/22
 * 描    述：Application
 * 修订历史：
 * ================================================
 */
class App : LitePalApplication() {

    override fun onCreate() {
        super.onCreate()

        //Bugly
        CrashReport.initCrashReport(this, "fd56549a7e", AppUtils.isAppDebug())
        //工具基类初始化
        Utils.init(this)
        //ARouter初始化
        ARouter.init(this)
        //mmkv初始化
        MMKVUtils.init(this)
        //文件管理初始化
        FileOperator.init(this, AppUtils.isAppDebug())
        //Log初始化
        initLog()
        //初始化Net
        initNet()
        //初始化BRV
        initBRV()
        //Bmob初始化
        Bmob.initialize(this, "78aed97a7944d2d90a14f0a0e3ae363d");

        //Debug模式下开启
        if (AppUtils.isAppDebug()) {
            ARouter.openLog()
            ARouter.openDebug()
            CrashUtils.init()
//            DoraemonKit.install(this)
        }
    }

    //初始化日志工具类
    private fun initLog() {
        val config = LogUtils.getConfig()
            .setLogSwitch(AppUtils.isAppDebug()) // 设置 log 总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(AppUtils.isAppDebug()) // 设置是否输出到控制台开关，默认开
            .setGlobalTag(null) // 设置 log 全局标签，默认为空
            // 当全局标签不为空时，我们输出的 log 全部为该 tag，
            // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
            .setLogHeadSwitch(true) // 设置 log 头信息开关，默认为开
            .setLog2FileSwitch(true) // 打印 log 时是否存到文件的开关，默认关
            .setDir("") // 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("") // 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd$fileExtension"
            .setFileExtension(".log") // 设置日志文件后缀
            .setBorderSwitch(true) // 输出日志是否带边框开关，默认开
            .setSingleTagSwitch(true) // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
            .setConsoleFilter(LogUtils.V) // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
            .setFileFilter(LogUtils.E) // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
            .setStackDeep(1) // log 栈深度，默认为 1
            .setStackOffset(0) // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
            .setSaveDays(1) // 设置日志可保留天数，默认为 -1 表示无限时长
            // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
            .addFormatter(object : LogUtils.IFormatter<ArrayList<*>>() {
                override fun format(arrayList: ArrayList<*>): String {
                    return "LogUtils Formatter ArrayList { $arrayList }"
                }
            })
        LogUtils.i(config.toString())
    }

    //初始化Net
    private fun initNet() {
        NetConfig.initialize(host = Host.BASE_HOST, this) {
            // 超时设置
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            //添加Cookie
            cookieJar(CookieManger(applicationContext))
            //添加日志拦截器
            addInterceptor(
                LoggingInterceptor.Builder()
                    .setLevel(if (AppUtils.isAppDebug()) Level.BASIC else Level.NONE)
                    .log(Log.INFO)
                    .build()
            )

            //添加解析器
            setConverter(GsonConverter())
            //作用域发生异常是否打印
            setDebug(AppUtils.isAppDebug())
            //添加请求拦截器
            setRequestInterceptor(object : RequestInterceptor {
                override fun interceptor(request: BaseRequest) {
                    // TODO: 2022/4/27
                    //request.addHeader("token",token )
                }
            })
            //信任所有证书
            trustSSLCertificate()
            //全局错误处理器
            setErrorHandler(object : NetErrorHandler {
                override fun onError(e: Throwable) {
                    e.apply {
                        when (code) {
                            401 -> {
                                // TODO: 2022/4/27
                                //logout
                            }

                            else -> {
                                printStackTrace()
                                throwToast()
                            }
                        }
                    }
                }
            })
        }
    }

    //初始化BindingAdapter
    private fun initBRV() {
        BRV.modelId = BR.rvModel

        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.setHeaderHeight(64f)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _, _ ->
            MaterialHeader(this).apply {
                setColorSchemeColors(color(R.color.colorPrimary))
                setSize(32)
            }
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { _, _ ->
            ClassicsFooter.REFRESH_FOOTER_FAILED = string(R.string.srl_classic_footer_failed)
            ClassicsFooter.REFRESH_FOOTER_FINISH = string(R.string.srl_classic_footer_finish)
            ClassicsFooter.REFRESH_FOOTER_LOADING = string(R.string.srl_classic_footer_loading)
            ClassicsFooter.REFRESH_FOOTER_NOTHING = string(R.string.srl_classic_footer_nothing)
            ClassicsFooter.REFRESH_FOOTER_PULLING = string(R.string.srl_classic_footer_pulling)
            ClassicsFooter.REFRESH_FOOTER_REFRESHING =
                string(R.string.srl_classic_footer_refreshing)
            ClassicsFooter.REFRESH_FOOTER_RELEASE = string(R.string.srl_classic_footer_release)
            ClassicsFooter(this).apply {
                setTextSizeTitle(13f)
                setDrawableArrowSize(18f)
                setDrawableProgressSize(18f)
            }
        }

        StateConfig.apply {
            emptyLayout = R.layout.layout_state_empty
            errorLayout = R.layout.layout_state_error
            loadingLayout = R.layout.layout_state_loading
            setRetryIds(R.id.tv_error_retry)

            onError {
                startStateAnimation()
                if (it is String) {
                    findViewById<TextView>(R.id.tv_error).text = it
                }
            }
            onEmpty {
                startStateAnimation()
            }
            onContent {
                startStateAnimation()
            }
            onLoading {
                startStateAnimation()
            }
        }
    }
}