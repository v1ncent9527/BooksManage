package com.vincent.android.architecture.main.mine.todo

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
import com.lxj.xpopup.XPopup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.extention.userModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityTodoBinding
import com.vincent.android.architecture.main.dialog.TodoAddDialog
import com.vincent.android.architecture.main.model.TodoModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/29
 * 描    述：任务清单
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Mine.A_TODO)
class TodoActivity : BaseToolbarActivity<ActivityTodoBinding, TodoViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_todo
    }

    override fun initVariableId(): Int {
        return BR.todoVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "任务清单")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<TodoModel> {
                R.layout.rv_item_todo
            }
            onClick(R.id.rv_item) {
                if (getModel<TodoModel>().finished) return@onClick

                confirmDialog(this@TodoActivity, content = "确认标记该任务为已完成？") {
                    val todoModel = TodoModel(
                        System.currentTimeMillis(),
                        userModel!!.id,
                        getModel<TodoModel>().title,
                        getModel<TodoModel>().content,
                        true,
                        finishData = System.currentTimeMillis()
                    )
                    loading()
                    todoModel.update(getModel<TodoModel>().objectId, object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            hideLoading()
                            if (ObjectUtils.isEmpty(e)) {
                                toast("标记成功！")
                                binding.prl.showLoading()
                            } else {
                                toast(e?.message!!)
                            }
                        }
                    })
                }
            }
        }

        binding.imgAdd.click {
            XPopup.Builder(this).asCustom(TodoAddDialog(
                context = this,
            ) { title, content ->
                loading()
                val todoModel = TodoModel(
                    System.currentTimeMillis(),
                    userModel!!.id,
                    title,
                    content,
                    false,
                )
                todoModel.save(object : SaveListener<String>() {
                    override fun done(objectId: String?, e: BmobException?) {
                        hideLoading()
                        if (!objectId.isNullOrEmpty()) {
                            binding.prl.showLoading()
                        } else {
                            toast(e?.message!!)
                        }
                    }
                })
            }).show()
        }

        binding.prl.onRefresh {
            BmobQuery<TodoModel>().addWhereEqualTo("userId", userModel!!.id).order("-updatedAt")
                .findObjects(object : FindListener<TodoModel?>() {
                    override fun done(list: List<TodoModel?>?, e: BmobException?) {
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
}