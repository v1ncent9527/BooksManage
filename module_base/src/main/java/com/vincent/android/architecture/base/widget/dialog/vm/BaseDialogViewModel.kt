package com.vincent.android.architecture.base.widget.dialog.vm

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.databinding.BindingClick
import com.vincent.android.architecture.base.databinding.LiveDataEvent
import com.vincent.android.architecture.base.extention.string

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2021/10/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
open class BaseDialogViewModel(application: Application) : AndroidViewModel(application) {
    val title = ObservableField("")
    val subTitle = ObservableField("")
    val content = ObservableField("")
    val cancelVisible = ObservableField(false)
    val dividerVisible = ObservableField(true)
    val btnLeftText = ObservableField(string(R.string.common_cancel))
    val btnRightText = ObservableField(string(R.string.common_ensure))

    //点击监听
    val clickObserver = LiveDataEvent<String>()

    //返回
    val backClick = BindingClick {
        clickObserver.value = "backClick"
    }

    //取消
    val cancelClick = BindingClick {
        clickObserver.value = "cancelClick"
    }

    //确定
    val ensureClick = BindingClick {
        clickObserver.value = "ensureClick"
    }
    val ensureEnable = ObservableField(true)

    //重置
    val resetClick = BindingClick {
        clickObserver.value = "resetClick"
    }
}