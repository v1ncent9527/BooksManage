package com.vincent.android.architecture.main.index.booklist

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBookListBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/27
 * 描    述：书籍列表
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Index.A_BOOK_LIST)
class BookListActivity :
    BaseToolbarActivity<ActivityBookListBinding, BookListViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_list
    }

    override fun initVariableId(): Int {
        return BR.bookListVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "书籍列表")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDivider(16, true)
            includeVisible = true
        }.setup {
            addType<String> { R.layout.rv_item_book_list }
        }.models = mutableListOf("", "", "", "")
    }
}