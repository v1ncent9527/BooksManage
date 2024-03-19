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
 * 描    述：租借
 * 修订历史：
 * ================================================
 */
@Parcelize
data class LeaseModel(
    val id: Long,
    val userId: Long, //用户id
    val bookId: Long, //图书id
    val bookImg: String, //图书图片
    val bookName: String,//图书图片
    val period: Int,    //租赁时常 单位：天
    val leaseInDate: Long,//租借时间
    val leaseOutDate: Long? = 0L,//归还时间
    val status: Int,  //0 - 在读  1 - 历史
) : BmobObject(), Parcelable {
    fun bindName() = "《${bookName}》"

    fun bindDate(): String {
        return if (status == 0) {
            val isTimeOut = (leaseInDate + period * 24 * 60 * 60 * 1000) < System.currentTimeMillis()
            if (isTimeOut) {
                "已超过归还期限，请尽快归还"
            } else {
                "请于${(leaseInDate + period * 24 * 60 * 60 * 1000).formatTime("yyyy/MM/dd HH:mm")}前归还"
            }
        } else {
            "已于${leaseOutDate?.formatTime("yyyy/MM/dd HH:mm")}归还"
        }
    }
}
