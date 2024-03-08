package com.vincent.android.architecture.base.extention

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.Gravity
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.drake.net.request.BodyRequest
import com.drake.net.request.MediaConst
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.config.C
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import java.util.Formatter
import java.util.Locale

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/25
 * 描    述：通用扩展
 * 修订历史：
 * ================================================
 */

///////////////////////////////////////////////////////////////////////////
// ToastUtils
///////////////////////////////////////////////////////////////////////////

/**
 * Toast
 * @param text text
 */
fun toast(text: String) {
    ToastUtils.make().setBgResource(R.drawable.shape_toast)
        .setTextColor(color(R.color.white))
        .setGravity(Gravity.CENTER, 0, 0).show(text)

}

fun toast(resId: Int) {
    ToastUtils.make().setBgResource(R.drawable.shape_toast)
        .setTextColor(color(R.color.white))
        .setGravity(Gravity.CENTER, 0, 0).show(resId)
}

///////////////////////////////////////////////////////////////////////////
// AppUtils
///////////////////////////////////////////////////////////////////////////

/**
 * 是否未登录状态
 */
// TODO: 2022/4/25
fun loginOrNot() = false

/**
 * 退出登录
 */
fun logout() {
    // TODO: 2022/4/25
}

/**
 * 复制到剪贴板
 */
fun String?.copy2Clipboard() {
    ClipboardUtils.copyText(this ?: "")
    toast(R.string.common_copy_to_clipboard)
}

/**
 * Restart APP
 */
fun Activity.restartApp() {
    val intent = baseContext.packageManager.getLaunchIntentForPackage(
        baseContext.packageName
    )
    intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    finish()
}

///////////////////////////////////////////////////////////////////////////
// GsonUtils
///////////////////////////////////////////////////////////////////////////

fun Any?.toJson(): String = GsonUtils.toJson(this)

inline fun <reified T> String.toBean(): T =
    GsonUtils.fromJson(this, object : TypeToken<T>() {}.type)

fun <T> JSONArray?.toList(clazz: Class<T>): List<T> {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val list = mutableListOf<T>()
    if (this == null || this.length() == 0) return list
    for (idx in 0 until this.length()) {
        val jsonString = this[idx]!!.toString()
        val javaObj = gson.fromJson(jsonString, clazz)
        list.add(javaObj)
    }
    return list
}

fun BodyRequest.gson(vararg body: Pair<String, Any?>) {
    this.body = Gson().toJson(body.toMap()).toRequestBody(MediaConst.JSON)
}

///////////////////////////////////////////////////////////////////////////
// ResourceUtils
///////////////////////////////////////////////////////////////////////////

// px to dp
val Int.dp: Int get() = SizeUtils.px2dp(this.toFloat())
val Float.dp: Float get() = SizeUtils.px2dp(this).toFloat()

// dp to px
val Int.px: Int get() = SizeUtils.dp2px(this.toFloat())
val Float.px: Float get() = SizeUtils.dp2px(this).toFloat()

// sp to px
val Int.sp: Int get() = SizeUtils.sp2px(this.toFloat())
val Float.sp: Float get() = SizeUtils.sp2px(this).toFloat()

fun color(resId: Int): Int = ColorUtils.getColor(resId)

fun string(resId: Int): String = StringUtils.getString(resId)

fun stringArray(resId: Int): Array<out String>? = StringUtils.getStringArray(resId)

fun drawable(resId: Int): Drawable = ResourceUtils.getDrawable(resId)

///////////////////////////////////////////////////////////////////////////
// RouteUtils
///////////////////////////////////////////////////////////////////////////

/**
 * 进入activity
 *
 * @param path
 * @param isNeedLogin 是否需要登录
 */
fun startARouterActivity(path: String?, isNeedLogin: Boolean = false) {
    if (isNeedLogin && !loginOrNot()) {
        login()
        return
    }
    ARouter.getInstance().build(path)
        .navigation()
}

/**
 * 进入activity 带动画
 *
 * @param path
 * @param isNeedLogin 是否需要登录
 */
fun startARouterActivity(
    path: String?,
    options: ActivityOptionsCompat?,
    isNeedLogin: Boolean = false
) {
    if (isNeedLogin && !loginOrNot()) {
        login()
        return
    }
    ARouter.getInstance().build(path)
        .withOptionsCompat(options)
        .navigation()
}

/**
 * 登录
 */
fun login() {
    // TODO: 2022/4/26
}

/**
 * H5
 */
fun h5(
    title: String? = null,
    url: String = "",
    isHtml: Boolean = false,
    htmlContent: String? = null,
) {
    ARouter.getInstance()
        .build(C.RouterPath.Common.A_H5)
        .withString("title", title)
        .withString("url", url)
        .withBoolean("isHtml", isHtml)
        .withString("htmlContent", htmlContent)
        .navigation()
}

/**
 * 扫描二维码
 */
fun qrcode() {
    ARouter.getInstance()
        .build(C.RouterPath.Common.A_QRCODE)
        .navigation()
}

///////////////////////////////////////////////////////////////////////////
// TimeUtils
///////////////////////////////////////////////////////////////////////////

fun Long.formatTime(dateFormat: String = "yyyy/MM/dd"): String =
    TimeUtils.millis2String(this, dateFormat)

fun millis2FitTimeSpan(timeMs: Long): String {
    if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
        return "00:00:00"
    }
    val totalSeconds = timeMs / 1000
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    return Formatter(StringBuilder(), Locale.getDefault()).format(
        "%02d:%02d:%02d",
        hours,
        minutes,
        seconds
    ).toString()
}