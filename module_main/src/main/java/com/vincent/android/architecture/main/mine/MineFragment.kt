package com.vincent.android.architecture.main.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import com.alibaba.android.arouter.launcher.ARouter
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.base.widget.dialog.ext.operateBottomDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.MineFragmentBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/26
 * 描    述：我的
 * 修订历史：
 * ================================================
 */
class MineFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<MineFragmentBinding, MineViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.mine_fragment
    }

    override fun initVariableId(): Int {
        return BR.mineVM
    }

    override fun initView() {
        binding.llSeatSelect.click {
            val list = listOf("1F-背书区", "2F-自习区", "3F-考研区", "4F-阅读区")
            operateBottomDialog(
                requireContext(),
                list
            ) {
                ARouter.getInstance()
                    .build(C.RouterPath.Mine.A_SEAT_SELECT)
                    .withInt("region", it)
                    .withString("title", list[it])
                    .navigation()
            }
        }
        binding.tvLogout.click {
            confirmDialog(context = requireContext(), content = "确认退出登录？") {
                BmobUser.logOut();
                startARouterActivity(C.RouterPath.Main.A_LOGIN)
                activity?.finish()
            }
        }
    }
}