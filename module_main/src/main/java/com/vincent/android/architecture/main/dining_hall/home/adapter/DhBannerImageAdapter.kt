package com.vincent.android.architecture.main.dining_hall.home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vincent.android.architecture.base.extention.loadRoundedUrl
import com.vincent.android.architecture.main.R
import com.vincent.android.architecture.main.dining_hall.model.NutritionModel
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2024/3/7
 * 描    述：banner适配器
 * 修订历史：
 * ================================================
 */
class DhBannerImageAdapter(data: MutableList<NutritionModel>?) :
    BannerAdapter<NutritionModel, DhBannerImageAdapter.Holder>(
        data
    ) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(BannerUtils.getView(parent!!, R.layout.layout_banner) as ImageView)
    }

    override fun onBindView(holder: Holder?, data: NutritionModel?, position: Int, size: Int) {
        holder?.imageView?.loadRoundedUrl(data?.img)
    }
}