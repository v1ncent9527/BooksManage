package com.vincent.android.architecture.base.net.ext

import com.blankj.utilcode.util.StringUtils
import com.drake.net.exception.*
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.extention.toast
import java.net.UnknownHostException

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/27
 * 描    述：Net异常处理类
 * 修订历史：
 * ================================================
 */

/**
 * 提示
 */
fun Throwable.throwToast() {
    toast(msg)
}

/**
 * code
 */
val Throwable.code: Int
    get() {
        return when (this) {
            is ConvertException -> response.code
            is RequestParamsException -> response.code
            is ServerResponseException -> response.code
            else -> -1
        }
    }

/**
 * msg
 */
val Throwable.msg: String
    get() {
        return when (this) {
            is UnknownHostException -> string(R.string.net_host_error)
            is URLParseException -> string(R.string.net_url_error)
            is NetConnectException -> string(R.string.net_network_error)
            is NetSocketTimeoutException -> StringUtils.getString(
                R.string.net_connect_timeout_error,
                message
            )
            is DownloadFileException -> string(R.string.net_download_error)
            is ConvertException -> string(R.string.net_parse_error)
            is RequestParamsException -> string(R.string.net_request_error)
            is ServerResponseException -> string(R.string.net_server_error)
            is NullPointerException -> string(R.string.net_null_error)
            is NoCacheException -> string(R.string.net_no_cache_error)
            is ResponseException -> message!!
            is NetException -> string(R.string.net_error)
            else -> string(R.string.net_other_error)
        }
    }

///////////////////////////////////////////////////////////////////////////
// 通用请求
///////////////////////////////////////////////////////////////////////////