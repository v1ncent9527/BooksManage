package com.vincent.android.architecture.main.dining_hall.model

import com.vincent.android.architecture.base.extention.color
import com.vincent.android.architecture.main.R

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/24
 * 描    述：
 * 修订历史：
 * ================================================
 */
data class TableModel(
    val no: Int,
    var isSelected: Boolean,
) {
    fun bindNo() = "${no}号餐桌"
    fun bindColor() = if (isSelected) color(R.color.green) else color(R.color.black_alpha_10)

}
