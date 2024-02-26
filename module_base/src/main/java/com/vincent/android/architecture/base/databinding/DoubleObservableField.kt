package com.vincent.android.architecture.base.databinding

import androidx.databinding.ObservableField

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
class DoubleObservableField(value: Double = 0.0) : ObservableField<Double>(value) {
    override fun get(): Double {
        return super.get()!!
    }
}