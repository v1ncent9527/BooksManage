package com.vincent.android.architecture.base.extention

import com.blankj.utilcode.util.LogUtils

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/23
 * 描    述：日志打印
 * 修订历史：
 * ================================================
 */
fun Any?.logV(tag: String? = null) = LogUtils.vTag(tag, this)
fun Any?.logI(tag: String? = null) = LogUtils.iTag(tag, this)
fun Any?.logW(tag: String? = null) = LogUtils.wTag(tag, this)
fun Any?.logE(tag: String? = null) = LogUtils.eTag(tag, this)
fun String?.logJson(tag: String? = null) = LogUtils.json(tag, this)