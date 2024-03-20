package com.vincent.android.architecture.main.dining_hall.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseFragment
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.startARouterActivity
import com.vincent.android.architecture.base.widget.dialog.ext.confirmDialog
import com.vincent.android.architecture.main.BR
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.databinding.FragmentMyBinding

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/3/20
 * 描    述：我的
 * 修订历史：
 * ================================================
 */
class MyFragment(override val immersionBarEnable: Boolean = false) :
    BaseFragment<FragmentMyBinding, MyViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_my
    }

    override fun initVariableId(): Int {
        return BR.myVM
    }

    override fun initView() {
        //退出登录
        binding.tvLogout.click {
            confirmDialog(context = requireContext(), content = "确认退出登录？") {
                BmobUser.logOut();
                startARouterActivity(C.RouterPath.Main.A_LOGIN)
                activity?.finish()
            }
        }
    }
}