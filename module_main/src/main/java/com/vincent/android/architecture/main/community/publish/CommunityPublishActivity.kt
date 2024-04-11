package com.vincent.android.architecture.main.community.publish

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.sendEvent
import com.lxj.xpopup.XPopup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.add
import com.vincent.android.architecture.base.extention.div
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityCommunityPublishBinding
import com.vincent.android.architecture.main.dialog.CommunityPublishDialog
import com.vincent.android.architecture.main.model.BookModel
import com.vincent.android.architecture.main.model.CommunityModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/8
 * 描    述：书评圈 发布
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Community.A_COMMUNITY_PUBLISH)
class CommunityPublishActivity :
    BaseToolbarActivity<ActivityCommunityPublishBinding, BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_community_publish
    }

    override fun initVariableId(): Int {
        return BR.communityPublishVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "请选择图书")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<BookModel> { R.layout.rv_item_book_market }

            onClick(R.id.rv_item) {
                XPopup.Builder(this@CommunityPublishActivity).asCustom(CommunityPublishDialog(
                    context = this@CommunityPublishActivity,
                    bookModel = getModel()
                ) { content, score ->
                    loading()

                    val model = getModel<BookModel>().copy(
                        score = (div(
                            add(
                                getModel<BookModel>().score,
                                score
                            ), 2.0, 1
                        ))
                    )
                    model.update(
                        getModel<BookModel>().objectId,
                        object : UpdateListener() {
                            override fun done(e: BmobException?) {
                                hideLoading()
                                if (ObjectUtils.isEmpty(e)) {
                                    val communityModel = CommunityModel(
                                        System.currentTimeMillis(),
                                        userModel!!.nickname,
                                        getModel<BookModel>().name,
                                        model.score,
                                        content,
                                        System.currentTimeMillis(),
                                    )
                                    communityModel.save(object : SaveListener<String>() {
                                        override fun done(
                                            objectId: String?,
                                            e: BmobException?
                                        ) {
                                            hideLoading()
                                            if (!objectId.isNullOrEmpty()) {
                                                toast("发表成功！")
                                                sendEvent("success", C.BusTAG.COMMUNITY_PUBLISH)
                                                finish()
                                            } else {
                                                toast(e?.message!!)
                                            }
                                        }
                                    })
                                } else {
                                    toast(e?.message!!)
                                }
                            }
                        })
                }).show()
            }
        }

        binding.prl.onRefresh {
            BmobQuery<BookModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<BookModel?>() {
                    override fun done(list: List<BookModel?>?, e: BmobException?) {
                        list?.let {
                            binding.rv.models = it
                            showContent()
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
}