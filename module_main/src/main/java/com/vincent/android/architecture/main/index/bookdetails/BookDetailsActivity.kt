package com.vincent.android.architecture.main.index.bookdetails

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.sendEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.gone
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.extention.visible
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.model.UserModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBookDetailsBinding
import com.vincent.android.architecture.main.model.BookModel
import com.vincent.android.architecture.main.model.LeaseModel


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
    BaseToolbarActivity<ActivityBookDetailsBinding, BaseViewModel>() {

    @JvmField
    @Autowired(name = "bookModel")
    var bookModel: BookModel? = null

    @JvmField
    @Autowired(name = "objectId")
    var objectId: String? = null

    var leaseOnlineModel: LeaseModel? = null

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_details
    }

    override fun initVariableId(): Int {
        return BR.bookDetailsVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "书籍详情")
    }

    override fun initView() {
        binding.bookModel = bookModel

        binding.rv.linear()
            .divider {
                setDrawable((R.drawable.shape_rv_divider_linear))
                endVisible = true
            }.setup {
                addType<UserModel> { R.layout.rv_item_on_lease_user }
            }

        binding.tvLeaseIn.click {
            confirmDialog(
                context = this,
                content = "确认租/租借《${bookModel!!.name}》？租赁为10天，请留意归还日期！"
            ) {
                loading()
                if (ObjectUtils.isEmpty(leaseOnlineModel)) {
                    val leaseModel = LeaseModel(
                        System.currentTimeMillis(),
                        userModel!!.id,
                        bookModel!!.id,
                        bookModel!!.logoUrl,
                        bookModel!!.name,
                        10,
                        System.currentTimeMillis(),
                        status = 0,
                    )
                    leaseModel.save(object : SaveListener<String>() {
                        override fun done(objectId: String?, e: BmobException?) {
                            hideLoading()
                            if (!objectId.isNullOrEmpty()) {
                                toast("租借成功！")
                                sendEvent("success", C.BusTAG.LEASE_STATUE)
                                initData()
                            } else {
                                toast(e?.message!!)
                            }
                        }
                    })

                    val model = bookModel!!.copy()
                    model.objectId = objectId
                    if (model.userList.isNullOrEmpty()) {
                        model.userList = mutableListOf()
                    }
                    model.userList?.add(userModel!!)
                    model.update(model.objectId, object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            e?.let {
                                toast(e.message!!)
                            }
                        }
                    })
                } else {
                    val leaseModel = LeaseModel(
                        leaseOnlineModel!!.id,
                        leaseOnlineModel!!.userId,
                        leaseOnlineModel!!.bookId,
                        leaseOnlineModel!!.bookImg,
                        leaseOnlineModel!!.bookName,
                        10,
                        System.currentTimeMillis(),
                        status = 0,
                    )
                    leaseModel.update(leaseOnlineModel!!.objectId, object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            hideLoading()
                            if (ObjectUtils.isEmpty(e)) {
                                toast("已成功续租10天！")
                                sendEvent("success", C.BusTAG.LEASE_STATUE)
                                initData()
                            } else {
                                toast(e?.message!!)
                            }
                        }
                    })
                }
            }
        }

        binding.tvLeaseOut.click {
            if (ObjectUtils.isEmpty(leaseOnlineModel)) return@click
            confirmDialog(context = this, content = "确认归还《${bookModel!!.name}》？") {
                val leaseModel = LeaseModel(
                    leaseOnlineModel!!.id,
                    leaseOnlineModel!!.userId,
                    leaseOnlineModel!!.bookId,
                    leaseOnlineModel!!.bookImg,
                    leaseOnlineModel!!.bookName,
                    leaseOnlineModel!!.period,
                    leaseOnlineModel!!.leaseInDate,
                    System.currentTimeMillis(),
                    1,
                )
                loading()
                leaseModel.update(leaseOnlineModel!!.objectId, object : UpdateListener() {
                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun done(e: BmobException?) {
                        hideLoading()
                        if (ObjectUtils.isEmpty(e)) {

                            val model = bookModel!!.copy()
                            model.objectId = objectId
                            val list = mutableListOf<UserModel>()
                            model.userList?.forEach {
                                if (it.id != userModel!!.id)
                                    list.add(it)
                            }
                            model.userList = list

                            model.update(model.objectId, object : UpdateListener() {
                                override fun done(e: BmobException?) {
                                }
                            })

                            toast("归还成功！")
                            sendEvent("success", C.BusTAG.LEASE_STATUE)
                            initData()
                        } else {
                            toast(e?.message!!)
                        }
                    }
                })
            }
        }
    }

    override fun initData() {
        BmobQuery<LeaseModel>().addWhereEqualTo("userId", userModel!!.id)
            .addWhereEqualTo("bookId", bookModel!!.id)
            .addWhereEqualTo("status", 0)
            .findObjects(object : FindListener<LeaseModel?>() {
                override fun done(list: List<LeaseModel?>?, e: BmobException?) {
                    list?.let {
                        if (it.isNotEmpty()) {
                            leaseOnlineModel = it[0]
                            binding.tvLeaseOut.visible()
                        } else {
                            leaseOnlineModel = null
                            binding.tvLeaseOut.gone()
                        }
                    }
                }
            })


        BmobQuery<BookModel>().getObject(objectId, object : QueryListener<BookModel>() {
            @SuppressLint("SetTextI18n")
            override fun done(bookModel: BookModel?, e: BmobException?) {
                if (null != bookModel) {
                    binding.rv.models = bookModel.userList
                    if (bookModel.userList.isNullOrEmpty()) {
                        binding.sl.showEmpty()
                        binding.tvUserno.text = "在借人员(0)"
                    } else {
                        binding.sl.showContent()
                        binding.tvUserno.text = "在借人员(${bookModel.userList!!.size})"
                    }
                } else {
                    binding.tvUserno.text = "在借人员(0)"
                    binding.sl.showEmpty()
                }
            }
        })
    }
}