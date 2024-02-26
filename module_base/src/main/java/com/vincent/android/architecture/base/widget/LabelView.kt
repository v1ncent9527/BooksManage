package com.vincent.android.architecture.base.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import com.vincent.android.architecture.base.R
import java.util.*

/**
 * ================================================
 * 作    者：v1ncet
 * 版    本：1.0.0
 * 创建日期：2022/4/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
open class LabelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var mTextContent: String? = null
    private var mTextColor = 0
    private var mTextSize = 0f
    private var mTextBold = false
    private var mFillTriangle = false
    private var mTextAllCaps = false
    private var mBackgroundColor = 0
    private var mMinSize = 0f
    private var mPadding = 0f

    /**
     * Gravity.TOP | Gravity.LEFT
     * Gravity.TOP | Gravity.RIGHT
     * Gravity.BOTTOM | Gravity.LEFT
     * Gravity.BOTTOM | Gravity.RIGHT
     */
    var gravity = 0
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()
    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LabelView)
        mTextContent = ta.getString(R.styleable.LabelView_lv_text)
        mTextColor = ta.getColor(R.styleable.LabelView_lv_text_color, Color.parseColor("#ffffff"))
        mTextSize = ta.getDimension(R.styleable.LabelView_lv_text_size, sp2px(11f).toFloat())
        mTextBold = ta.getBoolean(R.styleable.LabelView_lv_text_bold, true)
        mTextAllCaps = ta.getBoolean(R.styleable.LabelView_lv_text_all_caps, true)
        mFillTriangle = ta.getBoolean(R.styleable.LabelView_lv_fill_triangle, false)
        mBackgroundColor =
            ta.getColor(R.styleable.LabelView_lv_background_color, Color.parseColor("#FF4081"))
        mMinSize = ta.getDimension(
            R.styleable.LabelView_lv_min_size,
            (if (mFillTriangle) dp2px(35f) else dp2px(50f).toFloat()) as Float
        )
        mPadding = ta.getDimension(R.styleable.LabelView_lv_padding, dp2px(3.5f).toFloat())
        gravity = ta.getInt(R.styleable.LabelView_lv_gravity, Gravity.TOP or Gravity.LEFT)
        ta.recycle()
    }

    var text: String?
        get() = mTextContent
        set(text) {
            mTextContent = text
            invalidate()
        }
    var textColor: Int
        get() = mTextColor
        set(textColor) {
            mTextColor = textColor
            invalidate()
        }
    var textSize: Float
        get() = mTextSize
        set(textSize) {
            mTextSize = sp2px(textSize).toFloat()
            invalidate()
        }
    var isTextBold: Boolean
        get() = mTextBold
        set(textBold) {
            mTextBold = textBold
            invalidate()
        }
    var isFillTriangle: Boolean
        get() = mFillTriangle
        set(fillTriangle) {
            mFillTriangle = fillTriangle
            invalidate()
        }
    var isTextAllCaps: Boolean
        get() = mTextAllCaps
        set(textAllCaps) {
            mTextAllCaps = textAllCaps
            invalidate()
        }
    var bgColor: Int
        get() = mBackgroundColor
        set(backgroundColor) {
            mBackgroundColor = backgroundColor
            invalidate()
        }
    var minSize: Float
        get() = mMinSize
        set(minSize) {
            mMinSize = dp2px(minSize).toFloat()
            invalidate()
        }
    var padding: Float
        get() = mPadding
        set(padding) {
            mPadding = dp2px(padding).toFloat()
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        val size = height
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize
        mTextPaint.isFakeBoldText = mTextBold
        mBackgroundPaint.color = mBackgroundColor
        val textHeight = mTextPaint.descent() - mTextPaint.ascent()
        if (mFillTriangle) {
            if (gravity == Gravity.TOP or Gravity.LEFT) {
                mPath.reset()
                mPath.moveTo(0f, 0f)
                mPath.lineTo(0f, size.toFloat())
                mPath.lineTo(size.toFloat(), 0f)
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawTextWhenFill(size, -DEFAULT_DEGREES.toFloat(), canvas, true)
            } else if (gravity == Gravity.TOP or Gravity.RIGHT) {
                mPath.reset()
                mPath.moveTo(size.toFloat(), 0f)
                mPath.lineTo(0f, 0f)
                mPath.lineTo(size.toFloat(), size.toFloat())
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawTextWhenFill(size, DEFAULT_DEGREES.toFloat(), canvas, true)
            } else if (gravity == Gravity.BOTTOM or Gravity.LEFT) {
                mPath.reset()
                mPath.moveTo(0f, size.toFloat())
                mPath.lineTo(0f, 0f)
                mPath.lineTo(size.toFloat(), size.toFloat())
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawTextWhenFill(size, DEFAULT_DEGREES.toFloat(), canvas, false)
            } else if (gravity == Gravity.BOTTOM or Gravity.RIGHT) {
                mPath.reset()
                mPath.moveTo(size.toFloat(), size.toFloat())
                mPath.lineTo(0f, size.toFloat())
                mPath.lineTo(size.toFloat(), 0f)
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawTextWhenFill(size, -DEFAULT_DEGREES.toFloat(), canvas, false)
            }
        } else {
            val delta = (textHeight + mPadding * 2) * Math.sqrt(2.0)
            if (gravity == Gravity.TOP or Gravity.LEFT) {
                mPath.reset()
                mPath.moveTo(0f, (size - delta).toFloat())
                mPath.lineTo(0f, size.toFloat())
                mPath.lineTo(size.toFloat(), 0f)
                mPath.lineTo((size - delta).toFloat(), 0f)
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawText(size, -DEFAULT_DEGREES.toFloat(), canvas, textHeight, true)
            } else if (gravity == Gravity.TOP or Gravity.RIGHT) {
                mPath.reset()
                mPath.moveTo(0f, 0f)
                mPath.lineTo(delta.toFloat(), 0f)
                mPath.lineTo(size.toFloat(), (size - delta).toFloat())
                mPath.lineTo(size.toFloat(), size.toFloat())
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawText(size, DEFAULT_DEGREES.toFloat(), canvas, textHeight, true)
            } else if (gravity == Gravity.BOTTOM or Gravity.LEFT) {
                mPath.reset()
                mPath.moveTo(0f, 0f)
                mPath.lineTo(0f, delta.toFloat())
                mPath.lineTo((size - delta).toFloat(), size.toFloat())
                mPath.lineTo(size.toFloat(), size.toFloat())
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawText(size, DEFAULT_DEGREES.toFloat(), canvas, textHeight, false)
            } else if (gravity == Gravity.BOTTOM or Gravity.RIGHT) {
                mPath.reset()
                mPath.moveTo(0f, size.toFloat())
                mPath.lineTo(delta.toFloat(), size.toFloat())
                mPath.lineTo(size.toFloat(), delta.toFloat())
                mPath.lineTo(size.toFloat(), 0f)
                mPath.close()
                canvas.drawPath(mPath, mBackgroundPaint)
                drawText(size, -DEFAULT_DEGREES.toFloat(), canvas, textHeight, false)
            }
        }
    }

    private fun drawText(
        size: Int,
        degrees: Float,
        canvas: Canvas,
        textHeight: Float,
        isTop: Boolean
    ) {
        canvas.save()
        canvas.rotate(degrees, size / 2f, size / 2f)
        val delta = if (isTop) -(textHeight + mPadding * 2) / 2 else (textHeight + mPadding * 2) / 2
        val textBaseY = size / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2 + delta
        canvas.drawText(
            (if (mTextAllCaps) mTextContent!!.uppercase(Locale.getDefault()) else mTextContent)!!,
            (
                    paddingLeft + (size - paddingLeft - paddingRight) / 2).toFloat(),
            textBaseY,
            mTextPaint
        )
        canvas.restore()
    }

    private fun drawTextWhenFill(size: Int, degrees: Float, canvas: Canvas, isTop: Boolean) {
        canvas.save()
        canvas.rotate(degrees, size / 2f, size / 2f)
        val delta = if (isTop) (-size / 4).toFloat() else size / 4.toFloat()
        val textBaseY = size / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2 + delta
        canvas.drawText(
            (if (mTextAllCaps) mTextContent!!.uppercase(Locale.getDefault()) else mTextContent)!!,
            (
                    paddingLeft + (size - paddingLeft - paddingRight) / 2).toFloat(),
            textBaseY,
            mTextPaint
        )
        canvas.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = measureWidth(widthMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

    /** 确定View宽度大小  */
    private fun measureWidth(widthMeasureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec)
        if (specMode == MeasureSpec.EXACTLY) { //大小确定直接使用
            result = specSize
        } else {
            val padding = paddingLeft + paddingRight
            mTextPaint.color = mTextColor
            mTextPaint.textSize = mTextSize
            val textWidth = mTextPaint.measureText(mTextContent + "")
            result = ((padding + textWidth.toInt()) * Math.sqrt(2.0)).toInt()
            //如果父视图的测量要求为AT_MOST,即限定了一个最大值,则再从系统建议值和自己计算值中去一个较小值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
            result = Math.max(mMinSize.toInt(), result)
        }
        return result
    }

    private fun dp2px(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun sp2px(sp: Float): Int {
        val scale = resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

    companion object {
        private const val DEFAULT_DEGREES = 45
    }

    init {
        obtainAttributes(context, attrs)
        mTextPaint.textAlign = Paint.Align.CENTER
    }
}