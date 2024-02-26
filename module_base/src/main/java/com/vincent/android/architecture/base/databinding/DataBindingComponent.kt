package com.vincent.android.architecture.base.databinding

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.view.longClicks
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.extention.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/25
 * 描    述：DataBinding容器
 * 修订历史：
 * ================================================
 */

@BindingAdapter("viewVisibleAlpha")
fun View.viewVisibleAlpha(visibility: Boolean) = if (visibility) visible() else invisible()

@BindingAdapter("viewVisible")
fun View.viewVisible(visibility: Boolean) = if (visibility) visible() else gone()

/**
 *  Animate view visibility true/false to View.VISIBLE and View.GONE
 *  @param visibility final visible state
 */
@BindingAdapter("animatedVisibleAlpha")
fun View.animatedVisibleAlpha(visibility: Boolean) {
    if (visibility) {
        animateVisible()
    } else {
        animateInvisible()
    }
}

/**
 *  Animate view visibility true/false to View.VISIBLE and View.INVISIBLE
 *  @param visibility final visible state
 */
@BindingAdapter("animatedVisible")
fun View.animatedVisible(visibility: Boolean) {
    if (visibility) {
        animateVisible()
    } else {
        animateGone()
    }
}

/**
 *  Animate android:layout_marginStart from original value to new value specified in pixels
 *  @param pixels final margin
 *  @param duration animation duration
 */
@BindingAdapter("animateMarginStart", "marginAnimDuration", requireAll = false)
fun View.animatedMarginStart(pixels: Int, duration: Int? = null) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.also { params ->
        ValueAnimator.ofInt(params.marginStart, pixels).apply {
            duration?.let { setDuration(it.toLong()) }
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                params.marginStart = it.animatedValue as? Int ?: 0
                layoutParams = params
            }

            start()
        }
    }
}

/**
 *  Animate android:layout_marginEnd from original value to new value specified in pixels
 *  @param pixels final margin
 *  @param duration animation duration
 */
@BindingAdapter("animateMarginEnd", "marginAnimDuration", requireAll = false)
fun View.animatedMarginEnd(pixels: Int, duration: Int? = null) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.also { params ->
        ValueAnimator.ofInt(params.marginEnd, pixels).apply {
            duration?.let { setDuration(it.toLong()) }
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                params.marginEnd = it.animatedValue as? Int ?: 0
                layoutParams = params
            }

            start()
        }
    }
}

/**
 *  Animate view background color with TransitionDrawable
 *  @param drawable TransitionDrawable
 *  @param duration animation duration
 */
@BindingAdapter("animBgTransition", "animBgDirectedDuration", requireAll = false)
fun View.animatedColor(drawable: Drawable, duration: Int? = 500) {
    (drawable as? TransitionDrawable)?.also { transition ->
        LogUtils.e("animatedColor", "animatedColor")

        background = transition
        when (duration) {
            null, 0 -> {
            }
            else ->
                if (duration < 0) {
                    if (this.tag == true) {
                        transition.reverseTransition(duration.absoluteValue)
                    }
                } else {
                    LogUtils.e("animatedColor", "animatedColor")
                    this.tag = true
                    transition.startTransition(duration)
                }
        }
    }
}

/**
 *  Animate view elevation from original value to new value specified in pixels
 *  @param pixels final elevation
 *  @param duration animation duration
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter("animElevation", "animElevationDuration", requireAll = false)
fun View.animateElevation(pixels: Int, duration: Int? = null) {
    ValueAnimator.ofFloat(elevation, pixels.toFloat()).apply {
        duration?.let { setDuration(it.toLong()) }
        interpolator = DecelerateInterpolator()
        addUpdateListener {
            elevation = it.animatedValue as? Float ?: 0f
        }

        start()
    }
}

@BindingAdapter("android:enabled")
fun View.setEnableBind(enable: Boolean) {
    if (enable) enable() else disable(0.5f)
}

@BindingAdapter("selected")
fun View.setSelectedBind(selected: Boolean) {
    isSelected = selected
}

//防重复点击间隔(毫秒)
const val CLICK_INTERVAL = 300

class BindingClick(private val onClickListener: (() -> Unit)? = null) {
    fun call() {
        onClickListener?.invoke()
    }
}

/**
 * requireAll 是意思是是否需要绑定全部参数, false为否
 * View的onClick事件绑定
 * onClickCommand 绑定的命令,
 * isThrottleFirst 是否开启防止过快点击
 */
@BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
fun View.onClickCommand(clickCommand: BindingClick, isThrottleFirst: Boolean) {
    if (isThrottleFirst) {
        clicks()
            .subscribe {
                clickCommand.call()
            }
    } else {
        clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.MILLISECONDS)
            .subscribe {
                clickCommand.call()
            }
    }
}

/**
 * LongClick事件
 */
@BindingAdapter(value = ["onLongClickCommand"], requireAll = false)
fun View.onLongClickCommand(clickCommand: BindingClick) {
    longClicks()
        .subscribe {
            clickCommand.call()
        }
}

/**
 * Display time based on millisecond value
 * @param dateMilli
 * @param dateFormat
 */
@BindingAdapter(value = ["dateMilli", "dateFormat"], requireAll = false)
fun TextView.setDateFromMillis(
    dateMilli: Long?,
    dateFormat: String? = "yyyy-MM-dd HH:mm"
) {
    val finalFormat =
        if (dateFormat.isNullOrEmpty()) "yyyy-MM-dd HH:mm" else dateFormat
    text = TimeUtils.millis2String(dateMilli ?: 0L, finalFormat)
}

/**
 * 设置图片Url地址
 * @param imgUrl         图片地址
 */
@BindingAdapter("imgUrl")
fun ImageView.setImageUrl(imgUrl: String?) {
    load(imgUrl ?: "") {
        crossfade(true)
        placeholder(R.drawable.shape_image_placeholder)
        error(R.drawable.webp_image_error)
    }
}

/**
 * 设置圆形图片Url地址
 * @param circleImgUrl    图片地址
 */
@BindingAdapter("circleImgUrl")
fun ImageView.setCircleImageUrl(circleImgUrl: String?) {
    load(circleImgUrl ?: "") {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholder(R.drawable.shape_image_placeholder_circle)
        error(R.drawable.webp_image_error)
    }
}

@BindingAdapter("roundedImg2Url")
fun ImageView.setRoundedImageUrl(roundedImgUrl: String?) {
    load(roundedImgUrl ?: "") {
        crossfade(true)
        transformations(
            RoundedCornersTransformation(
                topLeft = 2F.px,
                topRight = 2F.px,
                bottomLeft = 2F.px,
                bottomRight = 2F.px
            )
        )
        placeholder(R.drawable.shape_image_placeholder_round_default)
        error(R.drawable.webp_image_error)
    }
}
