package com.fsh.android.wanandroidmvvm.module.navigation.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.CommonUtil
import com.fsh.android.wanandroidmvvm.module.navigation.model.NavigationLabelResponse
import com.fsh.android.wanandroidmvvm.module.navigation.model.NavigationTabNameResponse
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.navigation_label_item.view.*

/**
 * Created with Android Studio.
 * Description:
 */
class NavigationLabelAdapter (layoutId : Int, listData : MutableList<NavigationTabNameResponse>?)
    : BaseQuickAdapter<NavigationTabNameResponse, BaseViewHolder>(layoutId, listData) {
    override fun convert(viewHolder: BaseViewHolder?, item: NavigationTabNameResponse?) {
        viewHolder?.let {
                holder ->
            item?.let {
                holder.itemView.nav_label_material_card.rippleColor = ColorUtil.getOneColorStateList(mContext)
                holder.itemView.nav_label_material_card.strokeColor = ColorUtil.getColor(mContext)
                holder.itemView.nav_label_layout.adapter = object : TagAdapter<NavigationLabelResponse>(it.articles) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        t: NavigationLabelResponse?
                    ): View {
                        val tagView : TextView =
                            LayoutInflater.from(mContext).inflate(R.layout.flow_layout, parent,false) as TextView
                        tagView.setText(it.articles[position].title)
                        tagView.setTextColor(ColorUtil.randomColor())
                        return tagView
                    }
                }
                holder.itemView.nav_label_layout.setOnTagClickListener {_, position, _ ->
                    CommonUtil.startWebView(mContext, it.articles[position].link, it.articles[position].title)
                    return@setOnTagClickListener true
                }
            }
        }
    }
}