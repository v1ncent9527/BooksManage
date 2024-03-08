package com.vincent.android.architecture.main.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.blankj.utilcode.util.ObjectUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.channel.receiveEvent
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.CommunityFragmentBinding
import com.vincent.android.architecture.main.model.CommunityModel

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：书评圈
 * 修订历史：
 * ================================================
 */
class CommunityFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<CommunityFragmentBinding, CommunityViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.community_fragment
    }

    override fun initVariableId(): Int {
        return BR.communityVM
    }

    override fun initView() {
        binding.rv.linear().divider {
            setDivider(16, true)
            includeVisible = true
        }.setup {
            addType<CommunityModel> { R.layout.rv_item_community }
        }

        binding.prl.onRefresh {
            BmobQuery<CommunityModel>()
                .order("-updatedAt")
                .findObjects(object : FindListener<CommunityModel?>() {
                    override fun done(list: List<CommunityModel?>?, e: BmobException?) {
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
        receiveEvent<String>(C.BusTAG.COMMUNITY_PUBLISH) {
            binding.prl.showLoading()
        }
    }
}