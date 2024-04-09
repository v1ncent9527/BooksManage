package com.vincent.android.architecture.main.index.book_market

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.model.ToolbarModel
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.ActivityBookMarketBinding
import com.vincent.android.architecture.main.model.BookModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/11
 * 描    述：书城
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Index.A_BOOK_MARKET)
class BookMarketActivity : BaseToolbarActivity<ActivityBookMarketBinding, BaseViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_market
    }

    override fun initVariableId(): Int {
        return BR.bookMarketACVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = "书城")
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDrawable((R.drawable.shape_rv_divider_linear))
            endVisible = true
        }.setup {
            addType<BookModel> { R.layout.rv_item_book_market }

            onClick(R.id.rv_item) {
                ARouter.getInstance().build(C.RouterPath.Index.A_BOOK_DETAILS)
                    .withParcelable("bookModel", getModel<BookModel>())
                    .withString("objectId", getModel<BookModel>().objectId)
                    .navigation()
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