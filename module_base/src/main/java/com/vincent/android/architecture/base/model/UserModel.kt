package com.vincent.android.architecture.base.model

import android.os.Parcelable
import cn.bmob.v3.BmobUser
import kotlinx.parcelize.Parcelize

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Parcelize
data class UserModel(
    val id: Long = 0L, //id
    val nickname: String = "", //昵称
    val type: Int = 0, // 0 - 用户 1 -管理员
) : BmobUser(), Parcelable {

}