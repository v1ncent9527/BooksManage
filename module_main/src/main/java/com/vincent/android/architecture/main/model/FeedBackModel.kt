package com.vincent.android.architecture.main.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.color
import com.vincent.android.architecture.base.extention.formatTime
import com.vincent.android.architecture.main.R
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/19
 * 描    述：意见反馈
 * 修订历史：
 * ================================================
 */
@Parcelize
data class FeedBackModel(
    val id: Long,
    val userId: Long, //用户id
    val userName: String, //用户姓名
    val title: String,   //标题
    val content: String, //内容
    val date: Long,   //时间
    val statue: Int = 0    //0 - 未处理 1 - 已采纳 2 - 已拒绝
) : BmobObject(), Parcelable {
    fun bindDate() = date.formatTime("yyyy/MM/dd HH:mm")
    fun bindAccept() = if (statue == 0) "" else {
        if (statue == 1) "已采纳" else "已拒绝"
    }

    fun bindAcceptColor() = if (statue == 1) color(R.color.green) else color(R.color.red)
}