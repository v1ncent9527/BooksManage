package com.vincent.android.architecture.main.dining_hall.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.formatTime
import com.vincent.android.architecture.base.extention.userModel
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
    val userName: String,  //用户名
    val price: Double,  //价格
    val statue: Int,  //0 - 已完成 1 - 备餐中 2 - 配送中 3 - 配送出错 4 - 已取消
    val errorMsg: String = "", //出错原因
    val remark: String = "",  //备注
    val date: Long,  //下单时间
    val type: Int,  //0 - 堂食  1 - 配送
    val address: String,  //配送地址
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

    fun bindType() = if (type == 0) "堂食" else "外送"
    fun bindTypeAdmin() = if (type == 0) "$userName • 堂食" else "$userName • 外送"

    fun bindStatue() = when (statue) {
        0 -> {
            "已完成"
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

        4 -> {
            "已取消"
        }

        else -> {
            ""
        }
    }

    fun bindDate() = date.formatTime("yyyy/MM/dd HH:mm")
    fun bindRemarkVisible() = remark.isNotEmpty()
    fun bindErrorMsgVisible() = statue == 3 && errorMsg.isNotEmpty()
    fun bindDetailCancelVisible() = userModel!!.type != 1 && (statue == 1 || statue == 2)
    fun bindDetailArriveVisible() = userModel!!.type != 1 && type == 1 && statue == 2
    fun bindDetailStartSendVisible() = userModel!!.type == 1 && type == 1 && statue == 1
    fun bindQRVisible() = userModel!!.type != 1 && type == 0 && statue == 1
}
