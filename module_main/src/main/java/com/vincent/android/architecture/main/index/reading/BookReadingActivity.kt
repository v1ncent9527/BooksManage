package com.vincent.android.architecture.main.index.reading

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.receiveEvent
import com.drake.channel.sendEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBookReadingBinding
import com.vincent.android.architecture.main.model.LeaseModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/11
 * 描    述：在借书籍 / 历史书籍列表
 * 修订历史：
 * ================================================
 */

@Route(path = C.RouterPath.Index.A_BOOK_READING)
class BookReadingActivity : BaseToolbarActivity<ActivityBookReadingBinding, BaseViewModel>() {
    @JvmField
    @Autowired(name = "type")
    var type: Int? = 0 // 0- 在读 1 - 历史

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_reading
    }

    override fun initVariableId(): Int {
        return BR.bookReadingVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = if (type == 0) "在借书籍" else "历史书籍")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<LeaseModel> { R.layout.rv_item_lease_item }

            onClick(R.id.img_more) {
                operateBottomDialog(this@BookReadingActivity, listOf("续租", "归还")) {
                    confirmDialog(
                        context = this@BookReadingActivity,
                        content = if (it == 0)
                            "确认租/租借《${getModel<LeaseModel>().bookName}》？租赁为10天，请留意归还日期！"
                        else
                            "确认归还《${getModel<LeaseModel>().bookName}》？"
                    ) {
                        when (it) {
                            0 -> {
                                //续租
                                val leaseModel = LeaseModel(
                                    getModel<LeaseModel>().id,
                                    getModel<LeaseModel>().userId,
                                    getModel<LeaseModel>().bookId,
                                    getModel<LeaseModel>().bookImg,
                                    getModel<LeaseModel>().bookName,
                                    10,
                                    System.currentTimeMillis(),
                                    status = 0,
                                )
                                leaseModel.update(getModel<LeaseModel>().objectId,
                                    object : UpdateListener() {
                                        override fun done(e: BmobException?) {
                                            hideLoading()
                                            if (ObjectUtils.isEmpty(e)) {
                                                toast("已成功续租10天！")
                                                sendEvent("success", C.BusTAG.LEASE_STATUE)
                                            } else {
                                                toast(e?.message!!)
                                            }
                                        }
                                    })
                            }

                            1 -> {
                                //归还
                                val leaseModel = LeaseModel(
                                    getModel<LeaseModel>().id,
                                    getModel<LeaseModel>().userId,
                                    getModel<LeaseModel>().bookId,
                                    getModel<LeaseModel>().bookImg,
                                    getModel<LeaseModel>().bookName,
                                    getModel<LeaseModel>().period,
                                    getModel<LeaseModel>().leaseInDate,
                                    System.currentTimeMillis(),
                                    1,
                                )
                                loading()
                                leaseModel.update(getModel<LeaseModel>().objectId,
                                    object : UpdateListener() {
                                        override fun done(e: BmobException?) {
                                            hideLoading()
                                            if (ObjectUtils.isEmpty(e)) {
                                                toast("归还成功！")
                                                sendEvent("success", C.BusTAG.LEASE_STATUE)
                                            } else {
                                                toast(e?.message!!)
                                            }
                                        }
                                    })
                            }
                        }
                    }
                }
            }
        }

        binding.prl.onRefresh {
            BmobQuery<LeaseModel>().addWhereEqualTo("userId", userModel!!.id)
                .addWhereEqualTo("status", type).findObjects(object : FindListener<LeaseModel?>() {
                    override fun done(list: List<LeaseModel?>?, e: BmobException?) {
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
        receiveEvent<String>(C.BusTAG.LEASE_STATUE) {
            binding.prl.showLoading()
        }
    }
}