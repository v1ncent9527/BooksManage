package com.vincent.android.architecture.base.widget

import android.content.Context
import android.view.View
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
class MaterialHeader(context: Context) : MaterialHeader(context) {
    override fun setSize(size: Int): MaterialHeader {
        val thisView: View = this
        val metrics = thisView.resources.displayMetrics
        mCircleDiameter = (size * metrics.density).toInt()
        mCircleView.setImageDrawable(null)
        mProgress.updateSizes(size)
        mCircleView.setImageDrawable(mProgress)
        return this
    }
}