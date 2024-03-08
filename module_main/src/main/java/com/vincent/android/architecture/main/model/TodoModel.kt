package com.vincent.android.architecture.main.model

import android.os.Parcelable
import android.text.SpannableStringBuilder
import cn.bmob.v3.BmobObject
import com.blankj.utilcode.util.SpanUtils
import com.vincent.android.architecture.base.extention.formatTime
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Parcelize
data class TodoModel(
    val id: Long, //id
    val userId: Long, //用户id
    val title: String,   //标题
    val content: String, //内容
    var finished: Boolean, //是否完成
    var finishData: Long? = 0L //完成时间
) : BmobObject(), Parcelable {
    fun bindTitle(): SpannableStringBuilder =
        if (finished) {
            SpanUtils()
                .append(title)
                .setStrikethrough()
                .create()
        } else {
            SpanUtils()
                .append(title)
                .create()
        }

    fun bindFinishData() = if (finished) finishData?.formatTime() else ""
}
