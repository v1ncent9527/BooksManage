package com.vincent.android.architecture.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.extention.drawable

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/26
 * 描    述：带删除按钮的EditText
 * 修订历史：
 * ================================================
 */
class CleanableEditText(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {

    private var mClearDrawable: Drawable = drawable(R.drawable.svg_et_clear)

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        clearIconVisible(hasFocus() && !text.isNullOrEmpty())
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        clearIconVisible(focused && length() > 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                val drawable = compoundDrawables[2]
                drawable?.let {
                    if (event.x <= (width - paddingRight)
                        && event.x >= (width - paddingRight - drawable.bounds.width())
                    ) {
                        setText("")
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun clearIconVisible(visible: Boolean) {
        setCompoundDrawablesWithIntrinsicBounds(
            compoundDrawables[0],
            compoundDrawables[1],
            if (visible) mClearDrawable else null,
            compoundDrawables[3]
        )
    }
}