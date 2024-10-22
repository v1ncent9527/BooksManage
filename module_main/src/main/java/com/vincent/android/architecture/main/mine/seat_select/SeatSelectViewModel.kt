package com.vincent.android.architecture.main.mine.seat_select

import android.app.Application
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.StringObservableField

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
class SeatSelectViewModel(application: Application) :BaseViewModel(application) {
    val title = StringObservableField("背书区")
}