package com.vincent.android.architecture.main.model

import cn.bmob.v3.BmobUser
import android.os.Parcelable
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
    val nickname: String
) : BmobUser(), Parcelable {

}