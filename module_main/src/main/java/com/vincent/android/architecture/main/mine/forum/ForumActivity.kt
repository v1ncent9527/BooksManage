package com.vincent.android.architecture.main.mine.forum

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
import com.vincent.android.architecture.main.databinding.ActivityForumBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：论坛
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Mine.A_FORUM)
class ForumActivity : BaseToolbarActivity<ActivityForumBinding, ForumViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_forum
    }

    override fun initVariableId(): Int {
        return BR.forumVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "论坛")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDivider(16, true)
            includeVisible = true
        }.setup {
            addType<String> {
                R.layout.rv_item_forum
            }
        }._data = mutableListOf("", "", "", "")
    }
}