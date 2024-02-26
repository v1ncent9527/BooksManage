package com.vincent.android.architecture.base.widget

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/26
 * 描    述：通用ViewPagerAdapter
 * 修订历史：
 * ================================================
 */
class VpAdapter(
    private val fragmentManager: FragmentManager,
    private val fragmentList: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        this.fragmentManager.beginTransaction().show(fragment).commit()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = fragmentList[position]
        fragmentManager.beginTransaction().hide(fragment).commit()
    }
}