package com.vincent.android.architecture.main.dining_hall.home.admin.feedback

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
import com.vincent.android.architecture.main.databinding.ActivityDhFeedbackBinding
import com.vincent.android.architecture.main.dining_hall.model.DhFeedBackModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：反馈收集
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_DH_FEEDBACK)
class DhFeedbackActivity : BaseToolbarActivity<ActivityDhFeedbackBinding, DhFeedbackViewModel>() {
    private val isAdmin = userModel!!.type == 1

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_dh_feedback
    }

    override fun initVariableId(): Int {
        return BR.dhFeedbackVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "反馈收集")
    }

    override fun initView() {
        if (isAdmin) binding.prl.visible() else binding.llUser.visible()

        binding.rv.linear()
            .divider {
                setDivider(16, true)
                includeVisible = true
            }
            .setup {
                addType<DhFeedBackModel> { R.layout.rv_item_feedback_dh }

                onClick(R.id.img_more) {
                    operateBottomDialog(this@DhFeedbackActivity, listOf("采纳", "拒绝")) {
                        loading()
                        val feedBackModel = DhFeedBackModel(
                            getModel<DhFeedBackModel>().id,
                            getModel<DhFeedBackModel>().userId,
                            getModel<DhFeedBackModel>().userName,
                            getModel<DhFeedBackModel>().title,
                            getModel<DhFeedBackModel>().content,
                            System.currentTimeMillis(),
                            it + 1
                        )
                        feedBackModel.update(
                            getModel<DhFeedBackModel>().objectId,
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
            BmobQuery<DhFeedBackModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<DhFeedBackModel?>() {
                    override fun done(list: List<DhFeedBackModel?>?, e: BmobException?) {
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