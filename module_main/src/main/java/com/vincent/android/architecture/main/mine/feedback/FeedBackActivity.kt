package com.vincent.android.architecture.main.mine.feedback

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.extention.visible
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityFeedbackBinding
import com.vincent.android.architecture.main.model.FeedBackModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Mine.A_FEEDBACK)
class FeedBackActivity : BaseToolbarActivity<ActivityFeedbackBinding, FeedBackViewModel>() {

    private val isAdmin = userModel!!.type == 1
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_feedback
    }

    override fun initVariableId(): Int {
        return BR.feedBackVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = if (isAdmin) "意见反馈处理" else "意见反馈")
    }

    override fun initView() {
        if (isAdmin) binding.prl.visible() else binding.llUser.visible()

        binding.rv.linear()
            .divider {
                setDivider(16, true)
                includeVisible = true
            }
            .setup {
                addType<FeedBackModel> { R.layout.rv_item_feedback }

                onClick(R.id.img_more) {
                    operateBottomDialog(this@FeedBackActivity, listOf("采纳", "拒绝")) {
                        loading()
                        val feedBackModel = FeedBackModel(
                            getModel<FeedBackModel>().id,
                            getModel<FeedBackModel>().userId,
                            getModel<FeedBackModel>().userName,
                            getModel<FeedBackModel>().title,
                            getModel<FeedBackModel>().content,
                            System.currentTimeMillis(),
                            it + 1
                        )
                        feedBackModel.update(
                            getModel<FeedBackModel>().objectId,
                            object : UpdateListener() {
                                override fun done(e: BmobException?) {
                                    hideLoading()
                                    if (ObjectUtils.isEmpty(e)) {
                                        toast("操作成功！")
                                        binding.prl.showLoading()
                                    } else {
                                        toast(e?.message!!)
                                    }
                                }
                            })
                    }
                }
            }

        binding.prl.onRefresh {
            BmobQuery<FeedBackModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<FeedBackModel?>() {
                    override fun done(list: List<FeedBackModel?>?, e: BmobException?) {
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
        if (isAdmin) binding.prl.showLoading()
    }

}