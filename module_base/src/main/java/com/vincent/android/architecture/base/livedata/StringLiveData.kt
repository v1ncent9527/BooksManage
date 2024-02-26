package com.vincent.android.architecture.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
class StringLiveData : MutableLiveData<String>() {
    override fun getValue(): String {
        return super.getValue() ?: ""
    }
}