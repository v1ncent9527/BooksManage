package com.vincent.android.architecture.main.mine.focus_record

import android.app.Application
import com.drake.net.time.Interval
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.BooleanObservableField
import com.vincent.android.architecture.base.databinding.LiveDataEvent
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.extention.millis2FitTimeSpan
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
class FocusViewModel(application: Application) : BaseViewModel(application) {
    private val isTiming = BooleanObservableField(false)
    val isStart = BooleanObservableField(false)

    val type = LiveDataEvent<Unit>()
    val timeClick = BindingClick {
        if (isTiming.get()) {
            switchState(false)
            interval.pause()
        } else {
            if (!isStart.get()) {
                type.call()
            } else {
                switchState(true)
                interval.resume()
            }
        }
    }

    fun switchState(isActive: Boolean) {
        isTiming.set(isActive)
        timeDesc.set(if (isActive) "专注中" else "已暂停")
    }

    val time = StringObservableField("00:00:00")
    val timeDesc = StringObservableField("已暂停")
    val typeList = listOf("背书", "刷题", "复习")
    var typeSelect = ""
    private val interval = Interval(1, TimeUnit.SECONDS)

    fun startTime() {
        interval.reset()
        interval.subscribe {
            time.set(millis2FitTimeSpan(it * 1000))
        }.start()
    }

    fun resetTime() {
        interval.stop()
        time.set("00:00:00")
        timeDesc.set("已暂停")
        isTiming.set(false)
        isStart.set(false)
    }
}