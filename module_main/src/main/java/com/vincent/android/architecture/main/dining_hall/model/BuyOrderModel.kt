package com.vincent.android.architecture.main.dining_hall.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.formatTime
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Parcelize
data class BuyOrderModel(
    val id: Long,  //Id
    val userId: Long,  //用户Id
    val price: Double,  //价格
    val statue: Int,  //0 - 已送达 1 - 备餐中 2 - 配送中 3 - 配送出错 4 - 已取消
    val errorMsg: String = "", //出错原因
    val tableNo: Int = 0,  //桌号
    val remark: String = "",  //备注
    val date: Long,  //下单时间
    val orderList: List<DishModel>
) : BmobObject(), Parcelable {
    fun bindPrice() = "$price"
    fun bindPriceWithUnit() = "¥$price"
    fun bindAmount(): String {
        var amount = 0
        for (dishModel in orderList) {
            amount += dishModel.amount
        }
        return "共 $amount 件"
    }

    fun bindTableNo() = "${tableNo}号餐桌"

    fun bindStatue() = when (statue) {
        0 -> {
            "已送达"
        }

        1 -> {
            "备餐中"
        }

        2 -> {
            "配送中"
        }

        3 -> {
            "配送出错"
        }

        3 -> {
            "已取消"
        }

        else -> {
            ""
        }
    }

    fun bindDate() = date.formatTime("yyyy/MM/dd HH:mm")
    fun bindRemarkVisible() = remark.isNotEmpty()
    fun bindErrorMsgVisible() = statue == 3 && errorMsg.isNotEmpty()

}
