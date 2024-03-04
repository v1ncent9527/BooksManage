package com.vincent.android.architecture.main.mine.todo

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.lxj.xpopup.XPopup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.model.ToolbarModel
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

                getModel<TodoModel>().finished = true
                notifyItemChanged(modelPosition)
            }
        }.models = mutableListOf(
            TodoModel(
                "",
                "",
                "吃早饭",
                "吃早饭吃早饭吃早饭吃早饭吃早饭吃早饭",
                true
            ),
            TodoModel(
                "",
                "",
                "吃午饭",
                "吃午饭吃午饭吃午饭吃午饭吃午饭吃午饭吃午饭",
                false
            ),
            TodoModel(
                "",
                "",
                "吃晚饭",
                "吃早吃晚饭吃晚饭吃晚饭吃晚饭吃晚饭",
                false
            ),
        )

        binding.imgAdd.click {
            XPopup.Builder(this)
                .asCustom(
                    TodoAddDialog(
                        context = this,
                    ) { _title, _content ->
                        toast(_title + _content)
                    }
                ).show()
        }
    }
}