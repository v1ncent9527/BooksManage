package com.vincent.android.architecture.main.dialog

import android.app.Application
import com.vincent.android.architecture.base.databinding.StringObservableField
import com.vincent.android.architecture.base.widget.dialog.vm.BaseDialogViewModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
class MainDialogViewModel(application: Application) : BaseDialogViewModel(application) {
    val todoTitle = StringObservableField("")
    val todoContent = StringObservableField("")
}