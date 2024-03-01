package com.vincent.android.architecture.base.model

import android.graphics.drawable.Drawable
import android.view.View
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.extention.color
import com.vincent.android.architecture.base.extention.drawable

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：标题栏
 * 修订历史：
 * ================================================
 */
data class ToolbarModel(
    val leftIcon: Drawable = drawable(R.drawable.svg_back), //左边图标
    val leftIconVisible: Int = View.VISIBLE,
    val leftText: String = "",                              //左边文字
    val leftTextColor: Int = color(R.color.black),
    val leftTextVisible: Int = View.GONE,
    val titleText: String = "",                             //标题
    val titleTextColor: Int = color(R.color.black),
    val rightIcon: Drawable = drawable(R.drawable.svg_back),//右边图标
    val rightIconVisible: Int = View.GONE,
    val rightText: String = "",                             //右边文字
    val rightTextColor: Int = color(R.color.black),
    val rightTextVisible: Int = View.GONE,
    val rightButton: String = "",                           //右边按钮
    val rightButtonDrawable: Drawable = drawable(R.drawable.shape_circle_base),
    val rightButtonVisible: Int = View.GONE,
    val dividerVisible: Int = View.VISIBLE                     //分割线
)
