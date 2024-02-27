package com.vincent.android.architecture.main.index.bookdetails

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBookDetailsBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/27
 * 描    述：书籍详情
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Index.A_BOOK_DETAILS)
class BookDetailsActivity :
    BaseToolbarActivity<ActivityBookDetailsBinding, BookDetailsViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_details
    }

    override fun initVariableId(): Int {
        return BR.bookDetailsVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "书籍详情")
    }
}