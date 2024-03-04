package com.vincent.android.architecture.main.mine.forum.publish

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityForumPublishBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/4
 * 描    述：论坛发布
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Mine.A_FORUM_PUBLISH)
class ForumPublishActivity :
    BaseToolbarActivity<ActivityForumPublishBinding, ForumPublishViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_forum_publish
    }

    override fun initVariableId(): Int {
        return BR.forumPublishVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "论坛发布")
    }
}