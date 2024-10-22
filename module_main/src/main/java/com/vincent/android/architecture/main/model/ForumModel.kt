package com.vincent.android.architecture.main.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.formatTime
import com.vincent.android.architecture.base.extention.userModel
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/8
 * 描    述：论坛
 * 修订历史：
 * ================================================
 */
@Parcelize
data class ForumModel(
    val id: Long,
    val userId: Long, //用户id
    val userName: String, //用户姓名
    val title: String,   //标题
    val content: String, //内容
    val date: Long    //时间
) : BmobObject(), Parcelable {
    fun bindData() = date.formatTime("yyyy/MM/dd HH:mm")
    fun bindMoreVisible() = userModel!!.type == 1
}
