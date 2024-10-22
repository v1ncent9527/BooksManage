package com.vincent.android.architecture.base.extention

import android.animation.Animator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.vincent.android.architecture.base.R

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/25
 * 描    述：ViewExtention
 * 修订历史：
 * ================================================
 */

/**
 * 设置View的宽度
 */
fun View.width(width: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = width
    layoutParams = params
    return this
}

/**
 * 设置宽度，带有过渡动画
 * @param targetValue 目标宽度
 * @param duration 时长
 * @param action 可选行为
 */
fun View.animateWidth(
    targetValue: Int, duration: Long = 400, listener: Animator.AnimatorListener? = null,
    action: ((Float) -> Unit)? = null
) {
    post {
        ValueAnimator.ofInt(width, targetValue).apply {
            addUpdateListener {
                width(it.animatedValue as Int)
                action?.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
}

/**
 * 设置View的高度
 */
fun View.height(height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置高度，带有过渡动画
 * @param targetValue 目标高度
 * @param duration 时长
 * @param action 可选行为
 */
fun View.animateHeight(
    targetValue: Int,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null,
    action: ((Float) -> Unit)? = null
) {
    post {
        ValueAnimator.ofInt(height, targetValue).apply {
            addUpdateListener {
                height(it.animatedValue as Int)
                action?.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
}

/**
 * 设置View的宽度和高度
 * @param width 要设置的宽度
 * @param height 要设置的高度
 */
fun View.widthAndHeight(width: Int, height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = width
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置宽度和高度，带有过渡动画
 * @param targetWidth 目标宽度
 * @param targetHeight 目标高度
 * @param duration 时长
 * @param action 可选行为
 */
fun View.animateWidthAndHeight(
    targetWidth: Int,
    targetHeight: Int,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null,
    action: ((Float) -> Unit)? = null
) {
    post {
        val startHeight = height
        val evaluator = IntEvaluator()
        ValueAnimator.ofInt(width, targetWidth).apply {
            addUpdateListener {
                widthAndHeight(
                    it.animatedValue as Int,
                    evaluator.evaluate(it.animatedFraction, startHeight, targetHeight)
                )
                action?.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
}

/**
 * 设置View的margin
 * @param leftMargin 默认保留原来的
 * @param topMargin 默认是保留原来的
 * @param rightMargin 默认是保留原来的
 * @param bottomMargin 默认是保留原来的
 */
fun View.margin(
    leftMargin: Int = Int.MAX_VALUE,
    topMargin: Int = Int.MAX_VALUE,
    rightMargin: Int = Int.MAX_VALUE,
    bottomMargin: Int = Int.MAX_VALUE
): View {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    if (leftMargin != Int.MAX_VALUE)
        params.leftMargin = leftMargin
    if (topMargin != Int.MAX_VALUE)
        params.topMargin = topMargin
    if (rightMargin != Int.MAX_VALUE)
        params.rightMargin = rightMargin
    if (bottomMargin != Int.MAX_VALUE)
        params.bottomMargin = bottomMargin
    layoutParams = params
    return this
}

/**
 * 可见性相关
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

private const val BASE_DURATION_MILLIS = 200L

fun View.animateVisible(endAction: () -> Unit = {}) {
    if (visibility == View.VISIBLE) return
    visible()
    alpha = 0f
    animate().alpha(1f)
        .withEndAction { endAction() }
        .duration = BASE_DURATION_MILLIS
}

fun View.animateGone(endAction: () -> Unit = {}) {
    if (visibility == View.GONE) return
    alpha = 1f
    animate().alpha(0f)
        .withEndAction {
            gone()
            endAction()
        }
        .duration = BASE_DURATION_MILLIS
}

fun View.animateInvisible(endAction: () -> Unit = {}) {
    if (visibility == View.INVISIBLE) return
    alpha = 1f
    animate().alpha(0f)
        .withEndAction {
            invisible()
            endAction()
        }
        .duration = BASE_DURATION_MILLIS
}

/**
 * 切换View的可见性
 */
fun View.toggleVisibility() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

/**
 * 设置点击监听, 并实现事件节流，300毫秒内只允许点击一次
 */
var clickCache = hashMapOf<Int, Runnable>()
fun View.click(duration: Long = 300, action: (view: View) -> Unit) {
    if (id == View.NO_ID) id = View.generateViewId()
    setOnClickListener {
        if (!clickCache.containsKey(id)) {
            //unClicked
            clickCache[id] = Runnable { clickCache.remove(id) }
            action(it)
        }
        removeCallbacks(clickCache[id])
        postDelayed(clickCache[id], duration)
    }
}

/**
 * 设置长按监听
 */
fun View.longClick(action: (view: View) -> Boolean) {
    setOnLongClickListener {
        action(it)
    }
}

/**
 * 获取View的截图, 支持获取整个RecyclerView列表的长截图
 * 注意：调用该方法时，请确保View已经测量完毕，如果宽高为0，则将抛出异常
 */
fun View.toBitmap(): Bitmap {
    if (measuredWidth == 0 || measuredHeight == 0) {
        throw RuntimeException("please make sure that the View has been measured.")
    }
    return when (this) {
        is RecyclerView -> {
            this.scrollToPosition(0)
            this.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )

            val bmp = Bitmap.createBitmap(width, measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)

            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight)
                background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            this.draw(canvas)
            this.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)
            )
            bmp
        }
        else -> {
            val screenshot =
                Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_4444)
            val canvas = Canvas(screenshot)
            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight)
                background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            draw(canvas)
            screenshot
        }
    }
}

/**
 * 所有子View
 */
inline val ViewGroup.children
    get() = (0 until childCount).map { getChildAt(it) }

/**
 * 设置View不可用
 */
fun View.enableStatue(b: Boolean) {
    if (b) enable() else disable()
}

fun View.disable(value: Float = 0.5f) {
    isEnabled = false
    alpha = value
}

fun View.disableAll(value: Float = 0.55f) {
    isEnabled = false
    alpha = value
    if (this is ViewGroup) {
        children.forEach {
            it.disableAll()
        }
    }
}

/**
 * 设置View可用
 */
fun View.enable() {
    isEnabled = true
    alpha = 1f
}

fun View.enableAll() {
    isEnabled = true
    alpha = 1f
    if (this is ViewGroup) {
        children.forEach {
            it.enableAll()
        }
    }
}

fun View.alphaEnable(b: Boolean, value: Float = 0.5f) {
    alpha = if (b) 1f else value
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = true): View {
    if (layoutId == -1) {
        return this
    }
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

///////////////////////////////////////////////////////////////////////////
// EditText
///////////////////////////////////////////////////////////////////////////

/**
 * 显示密码文本
 */
fun EditText.showPasswordOrHide(isShow: Boolean) {
    if (isShow) showPassword() else hidePassword()
}

fun EditText.showPassword() {
    transformationMethod = HideReturnsTransformationMethod.getInstance()
    setSelection(text.length)
}

/**
 * 隐藏密码文本
 */
fun EditText.hidePassword() {
    transformationMethod = PasswordTransformationMethod.getInstance()
    setSelection(text.length)
}

/**
 * 动态设置最大长度限制
 */
fun EditText.maxLength(max: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
}

fun EditText.setTextWidthEndCursor(s: CharSequence) {
    setText(s)
    setSelection(text.toString().length)
}

///////////////////////////////////////////////////////////////////////////
// ImageView
///////////////////////////////////////////////////////////////////////////

fun ImageView.load(
    imgUrl: String?,
    placeholderRes: Drawable? = drawable(R.drawable.shape_image_placeholder),
    errorRes: Drawable? = drawable(R.drawable.webp_image_error)
) {
    load(imgUrl ?: "") {
        crossfade(true)
        placeholder(placeholderRes)
        error(errorRes)
    }
}

fun ImageView.load(
    uri: Uri?,
    placeholderRes: Drawable? = drawable(R.drawable.shape_image_placeholder),
    errorRes: Drawable? = drawable(R.drawable.webp_image_error)
) {
    uri?.let {
        load(it) {
            crossfade(true)
            placeholder(placeholderRes)
            error(errorRes)
        }
    }
}

fun ImageView.loadCircle(
    imgUrl: String?,
    placeholderRes: Drawable? = drawable(R.drawable.shape_image_placeholder_circle),
    errorRes: Drawable? = drawable(R.drawable.webp_image_error)
) {
    load(imgUrl ?: "") {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholder(placeholderRes)
        error(errorRes)
    }
}

fun ImageView.loadCircle(
    uri: Uri?,
    placeholderRes: Drawable? = drawable(R.drawable.shape_image_placeholder_circle),
    errorRes: Drawable? = drawable(R.drawable.webp_image_error)
) {
    uri?.let {
        load(it) {
            crossfade(true)
            transformations(CircleCropTransformation())
            placeholder(placeholderRes)
            error(errorRes)
        }
    }
}

fun ImageView.loadRoundedUrl(
    imgUrl: String?,
    placeholderRes: Drawable? = drawable(R.drawable.shape_image_placeholder_round_default),
    errorRes: Drawable? = drawable(R.drawable.webp_image_error),
    roundedTopLeft: Float = 10f,
    roundedTopRight: Float = 10f,
    roundedBottomLeft: Float = 10f,
    roundedBottomRight: Float = 10f
) {
    load(imgUrl ?: "") {
        crossfade(true)
        transformations(
            RoundedCornersTransformation(
                topLeft = roundedTopLeft,
                topRight = roundedTopRight,
                bottomLeft = roundedBottomLeft,
                bottomRight = roundedBottomRight
            )
        )
        placeholder(placeholderRes)
        error(errorRes)
    }
}

///////////////////////////////////////////////////////////////////////////
// StateLayout
///////////////////////////////////////////////////////////////////////////
fun View.startStateAnimation() {
    animate().setDuration(0).alpha(0F).withEndAction {
        animate().setDuration(400).alpha(1F)
    }
}