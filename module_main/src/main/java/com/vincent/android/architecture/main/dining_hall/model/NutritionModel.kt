package com.vincent.android.architecture.main.dining_hall.model

import android.os.Parcelable
import cn.bmob.v3.BmobObject
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/4/2
 * 描    述：营养
 * 修订历史：
 * ================================================
 */
@Parcelize
data class NutritionModel(
    val id: Long,
    val name: String,
    val img: String,
    val heat: String, //热量
    val fat: String, //脂肪
    val cellulose: String, //纤维素
    val carbohydrate: String, //碳水化合物
    val protein: String, //蛋白质
    val category: String, //类别
) : BmobObject(), Parcelable {

    fun bindCategory() = "分类：${category}"
    fun bindHeat() = "热量：${heat} 大卡（100克）"
}
