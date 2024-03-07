package com.vincent.android.architecture.main.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.drawable
import com.vincent.android.architecture.main.R
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/1
 * 描    述：座位
 * 修订历史：
 * ================================================
 */
@Parcelize
data class SeatModel(
    var id: Long,
    var no: Int,
    var type: Int,//0 - 可选  1 - 不可选  2 - 选中
    var region: Int,//0 - 1F 背书区  1 - 2F 自习区  2 - 3F 考研区 3 - 4F 阅读区
    var userId: Long? = null
) : BmobObject(), Parcelable {
    fun bindSeatDrawable(): Drawable =
        when (type) {
            1 -> drawable(R.drawable.svg_seat_selected)
            2 -> drawable(R.drawable.svg_seat_unselected)
            else -> drawable(R.drawable.svg_seat_blank)
        }
}
