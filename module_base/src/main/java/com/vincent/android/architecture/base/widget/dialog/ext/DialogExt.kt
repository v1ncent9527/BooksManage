package com.vincent.android.architecture.base.widget.dialog.ext

import android.content.Context
import android.graphics.drawable.Drawable
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.extention.drawable
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.widget.dialog.ConfirmDialog
import com.vincent.android.architecture.base.widget.dialog.OperateBottomDialog
import com.vincent.android.architecture.base.widget.dialog.OperateStatusDialog

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：Dialog扩展
 * 修订历史：
 * ================================================
 */

/**
 * 确认对话框
 *
 * @param context
 * @param title     标题
 * @param content   内容
 * @param onConfirmListener
 */
fun confirmDialog(
    context: Context,
    title: String = string(R.string.common_tips),
    content: String,
    onConfirmListener: (() -> Unit)? = null
) {
    XPopup.Builder(context)
        .popupAnimation(PopupAnimation.TranslateAlphaFromTop)
        .asCustom(
            ConfirmDialog(
                context = context,
                title = title,
                content = content
            ) {
                onConfirmListener?.invoke()
            }
        ).show()
}

/**
 * 操作提示相关对话框
 */
fun operateStatusDialog(
    context: Context,
    title: String = string(R.string.common_operate_success),
    statusImg: Drawable = drawable(R.drawable.svg_operate_success),
) {
    XPopup.Builder(context)
        .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
        .hasShadowBg(false)
        .dismissOnTouchOutside(false)
        .isClickThrough(true)
        .asCustom(
            OperateStatusDialog(
                context = context,
                title = title,
                statusImg = statusImg
            )
        ).show()
}


/**
 * 底部操作
 */
fun operateBottomDialog(
    context: Context,
    list: List<String>,
    onOperateBottomSelectListener: ((position: Int) -> Unit)
) {
    XPopup.Builder(context)
        .asCustom(
            OperateBottomDialog(
                context = context,
                list = list
            ) {
                onOperateBottomSelectListener.invoke(it)
            }
        ).show()
}