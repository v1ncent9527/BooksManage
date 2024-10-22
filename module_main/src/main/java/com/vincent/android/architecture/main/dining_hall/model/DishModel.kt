package com.vincent.android.architecture.main.dining_hall.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import com.vincent.android.architecture.base.extention.mul
import com.vincent.android.architecture.base.extention.roundDown
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Parcelize
data class DishModel(
    val id: Long,  //Id
    val name: String,  //菜名
    val desc: String,  //菜简介
    val type: Int,  //所属分类
    val imgUrl: String,  //菜名图片
    val materials: String,  //原料
    val price: Double,  //价格
    val costPrice: Double,  //成本价
    var amount: Int = 0, //数量
    var sold: Int = 0, //月售
) : BmobObject(), Parcelable {
    fun bindSold() = "月售$sold"
    fun bindPrice() = "$price"
    fun bindAmount() = "$amount"
    fun bindSoldSt() = "月销售额：${sold}份"
    fun bindPriceSt() = "销售价：¥$price"
    fun bindCostPriceSt() = "成本价：¥$costPrice"
    fun bindProfit() = "利润：¥${mul((price - costPrice), sold.toDouble()).roundDown(1)}"
    fun bindProfitDouble() = mul((price - costPrice), sold.toDouble())
}
