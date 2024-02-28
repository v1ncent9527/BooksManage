package com.vincent.android.architecture.main.model

import cn.bmob.v3.BmobObject

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/28
 * 描    述：书籍评价
 * 修订历史：
 * ================================================
 */
data class CommentModel(
    val bookId: String, //书籍ID
    val userId: String, //用户id
    val comment: String,//评论内容
) : BmobObject() {

}
