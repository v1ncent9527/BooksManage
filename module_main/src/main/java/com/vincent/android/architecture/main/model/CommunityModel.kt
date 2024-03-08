package com.vincent.android.architecture.main.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.formatTime
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/8
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Parcelize
data class CommunityModel(
    val id: Long,
    val userName: String,
    val bookName: String, //书籍名称
    val bookScore: Double, //书籍评分
    val content: String, //评论
    val date: Long    //时间
) : BmobObject(), Parcelable {

    fun bindBookName() = "《${bookName}》"

    fun bindBookScore() = "${bookScore}分"
    fun bindData() = date.formatTime("yyyy/MM/dd HH:mm")
}
