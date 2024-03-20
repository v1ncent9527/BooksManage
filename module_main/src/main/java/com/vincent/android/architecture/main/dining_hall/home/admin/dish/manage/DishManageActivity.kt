package com.vincent.android.architecture.main.dining_hall.home.admin.dish.manage

import ando.file.selector.FileSelector
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.load
import com.vincent.android.architecture.base.extention.observe
import com.vincent.android.architecture.base.extention.selectSingleImage
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.NumRangeInputFilter
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDishManageBinding


/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：菜品编辑
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.DiningHall.A_DISH_MANAGE)
class DishManageActivity : BaseToolbarActivity<ActivityDishManageBinding, DishManageViewModel>() {
    private var mFileSelector: FileSelector? = null

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_dish_manage
    }

    override fun initVariableId(): Int {
        return BR.dishManageVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "")
    }

    override fun initView() {
        binding.etPrice.filters = arrayOf(NumRangeInputFilter(100, 1))

        binding.imgAddPic.click {
            mFileSelector = selectSingleImage(this) { _, u, file ->
                viewModel.uploadPic(file!!)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFileSelector?.obtainResult(requestCode, resultCode, data)
    }

    override fun initData() {
    }

    override fun initObservable() {
        observe(viewModel.imgUploadUrlObserver) {
            toast("图片上传成功！")
            binding.imgAddPic.load(it)
        }
    }
}