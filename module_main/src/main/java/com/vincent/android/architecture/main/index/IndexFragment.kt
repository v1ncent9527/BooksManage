package com.vincent.android.architecture.main.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.IndexFragmentBinding
import com.vincent.android.architecture.main.index.adapter.BannerImageAdapter
import com.vincent.android.architecture.main.model.BookModel
import com.youth.banner.indicator.RectangleIndicator

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：主页
 * 修订历史：
 * ================================================
 */
class IndexFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<IndexFragmentBinding, IndexViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.index_fragment
    }

    override fun initVariableId(): Int {
        return BR.indexVM
    }

    override fun initView() {
        //轮播图
        binding.banner
            .setBannerGalleryEffect(6, 14)
            .setAdapter(BannerImageAdapter(mutableListOf()))
            .setIndicator(RectangleIndicator(requireContext()))
            .addBannerLifecycleObserver(this)
            .setOnBannerListener { data, _ ->
                (data as BookModel).apply {
                    ARouter.getInstance().build(C.RouterPath.Index.A_BOOK_DETAILS)
                        .withParcelable("bookModel", data)
                        .withString("objectId", data.objectId)
                        .navigation()
                }
            }

        //书城列表
        binding.rv.linear(orientation = RecyclerView.HORIZONTAL)
            .divider {
                setDivider(14, true)
                includeVisible = true
                orientation = DividerOrientation.HORIZONTAL
            }.setup {
                addType<BookModel> {
                    R.layout.rv_item_book_index
                }
                onClick(R.id.rv_item) {
                    ARouter.getInstance().build(C.RouterPath.Index.A_BOOK_DETAILS)
                        .withParcelable("bookModel", getModel<BookModel>())
                        .withString("objectId", getModel<BookModel>().objectId)
                        .navigation()
                }
            }

        //下拉刷新
        binding.prl.onRefresh {
            BmobQuery<BookModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<BookModel?>() {
                    override fun done(list: List<BookModel?>?, e: BmobException?) {
                        list?.let {
                            val size = if (it.size > 5) 5 else it.size

                            binding.banner.setDatas(it.subList(0, size))
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