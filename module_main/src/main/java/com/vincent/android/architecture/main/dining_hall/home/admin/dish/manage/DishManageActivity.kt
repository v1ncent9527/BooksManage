package com.vincent.android.architecture.main.dining_hall.home.admin.dish.manage

import ando.file.selector.FileSelector
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ObjectUtils
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.loadRoundedUrl
import com.vincent.android.architecture.base.extention.observe
import com.vincent.android.architecture.base.extention.selectSingleImage
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.base.widget.NumRangeInputFilter
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityDishManageBinding
import com.vincent.android.architecture.main.dining_hall.model.DishModel


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
    @JvmField
    @Autowired(name = "objectId")
    var objectId: String? = null

    @JvmField
    @Autowired(name = "dishModel")
    var dishModel: DishModel? = null

    private var mFileSelector: FileSelector? = null

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_dish_manage
    }

    override fun initVariableId(): Int {
        return BR.dishManageVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = if (ObjectUtils.isEmpty(dishModel)) "菜品上新" else "菜品编辑")
    }

    override fun initView() {
        binding.etPrice.filters = arrayOf(NumRangeInputFilter(100, 1))
        binding.etCostPrice.filters = arrayOf(NumRangeInputFilter(100, 1))

        dishModel?.let {
            viewModel.name.set(it.name)
            viewModel.desc.set(it.desc)
            viewModel.typeName.set(C.Common.dishList[it.type])
            viewModel.type = it.type
            viewModel.materials.set(it.materials)
            viewModel.price.set(it.price.toString())
            viewModel.costPrice.set(it.costPrice.toString())
            viewModel.imgUrl = it.imgUrl
            binding.imgAddPic.loadRoundedUrl(it.imgUrl)

            viewModel.dishModel = it
            viewModel.objectId = objectId
        }

        binding.imgAddPic.click {
            mFileSelector = selectSingleImage(this) { _, u, file ->
                viewModel.uploadPic(file!!)
            }
        }

        binding.imgUnfold.click {
            operateBottomDialog(this, C.Common.dishList) {
                viewModel.typeName.set(C.Common.dishList[it])
                viewModel.type = it
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
            binding.imgAddPic.loadRoundedUrl(it)
        }
    }
}