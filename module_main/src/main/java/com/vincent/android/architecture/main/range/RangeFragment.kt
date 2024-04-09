package com.vincent.android.architecture.main.range

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.RangeFragmentBinding
import com.vincent.android.architecture.main.model.BookModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：好书推荐
 * 修订历史：
 * ================================================
 */
class RangeFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<RangeFragmentBinding, BaseViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.range_fragment
    }

    override fun initVariableId(): Int {
        return BR.rangeVM
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<BookModel> { R.layout.rv_item_range }

            onClick(R.id.rv_item) {
                ARouter.getInstance().build(C.RouterPath.Index.A_BOOK_DETAILS)
                    .withParcelable("bookModel", getModel<BookModel>())
                    .withString("objectId", getModel<BookModel>().objectId)
                    .navigation()
            }
        }

        binding.prl.onRefresh {
            BmobQuery<BookModel>().order("-score")
                .findObjects(object : FindListener<BookModel?>() {
                    override fun done(list: List<BookModel?>?, e: BmobException?) {
                        list?.let {
                            val size = if (it.size > 10) 10 else it.size
                            it.forEachIndexed { index, bookModel ->
                                bookModel?.range = index + 1
                            }
                            binding.rv.models = it.subList(0, size)
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