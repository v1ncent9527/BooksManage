package com.vincent.android.architecture.main.mine.forum

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.receiveEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityForumBinding
import com.vincent.android.architecture.main.model.ForumModel

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
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<ForumModel> {
                R.layout.rv_item_forum
            }
        }

        binding.prl.onRefresh {
            BmobQuery<ForumModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<ForumModel?>() {
                    override fun done(list: List<ForumModel?>?, e: BmobException?) {
                        list?.let {
                            binding.rv.models = it
                            if (it.isEmpty()) showEmpty() else showContent()
                        }
                        if (!ObjectUtils.isEmpty(e)) {
                            showError(e?.message)
                        }
                    }
                })
        }
    }

    override fun initData() {
        binding.prl.showLoading()
    }

    override fun initObservable() {
        receiveEvent<String>(C.BusTAG.FORUM_PUBLISH) {
            binding.prl.showLoading()
        }
    }
}