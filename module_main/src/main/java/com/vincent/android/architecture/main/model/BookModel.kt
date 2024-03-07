package com.vincent.android.architecture.main.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/28
 * 描    述：书籍
 * 修订历史：
 * ================================================
 */
@Parcelize
data class BookModel(
    val id: Long,  //Id
    val name: String,  //书名
    val author: String,//作者
    val score: Double, //评分
    val summary: String, //简介
    val publishingHouse: String, //出版社
    val logoUrl: String, //书本图片
    var range: Int? = 0 //排名
) : BmobObject(), Parcelable {

    fun bindName() = "《${name}》"
    fun bindAuthor() = "$author 著"
    fun bindScore() = "${score}分"
    fun bindRange() = range.toString()
}
