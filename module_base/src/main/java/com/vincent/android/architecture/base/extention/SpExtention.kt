package com.vincent.android.architecture.base.extention

import com.vincent.android.architecture.base.utils.MMKVUtils
import kotlin.properties.Delegates

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/25
 * 描    述：SP本地数据
 * 修订历史：
 * ================================================
 */

/**
 * 是否进入app
 */
internal const val firstInAppKey = "firstInAppKey"
var firstInApp: Boolean? by Delegates.observable(
    MMKVUtils.defaultHolder()!!
        .decodeBool(firstInAppKey, false)
) { _, _, new ->
    MMKVUtils.defaultHolder()!!.encode(firstInAppKey, new!!)
}