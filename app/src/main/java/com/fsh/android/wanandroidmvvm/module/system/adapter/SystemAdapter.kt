package com.fsh.android.wanandroidmvvm.module.system.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fsh.android.wanandroidmvvm.custom.interpolator.CustomScaleInterpolator
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.startActivity
import com.fsh.android.wanandroidmvvm.module.system.view.SystemArticleListActivity
import com.fsh.android.wanandroidmvvm.module.system.model.SystemLabelResponse
import com.fsh.android.wanandroidmvvm.module.system.model.SystemTabNameResponse
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.system_item.view.*

/**
 * Created with Android Studio.
 * Description:
 */
class SystemAdapter(layoutId: Int, listData: MutableList<SystemTabNameResponse>?) :
    BaseQuickAdapter<SystemTabNameResponse, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SystemTabNameResponse?) {
        viewHolder?.let { holder ->
            holder.itemView.system_material_card.rippleColor =
                ColorUtil.getOneColorStateList(mContext)
            holder.itemView.system_material_card.strokeColor = ColorUtil.getColor(mContext)
            item?.let {
                holder.setText(R.id.item_system_title, it.name)
                holder.itemView.item_tag_layout.adapter =
                    object : TagAdapter<SystemLabelResponse>(it.children) {
                        override fun getView(
                            parent: FlowLayout?,
                            position: Int,
                            t: SystemLabelResponse?
                        ): View {
                            val tagView: TextView =
                                LayoutInflater.from(mContext)
                                    .inflate(R.layout.flow_layout, parent, false) as TextView
                            tagView.setText(it.children[position].name)
                            tagView.setTextColor(ColorUtil.randomColor())
                            return tagView
                        }
                    }

                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.BR_TL,
                    intArrayOf(
                        ColorUtil.calculateGradient(0.5f, ColorUtil.randomColor(), Color.WHITE),
                        Color.WHITE
                    )
                )
                holder.itemView.system_card.setBackgroundDrawable(gradientDrawable)

                holder.itemView.item_tag_layout.setOnTagClickListener { _, position, _ ->
                    startActivity<SystemArticleListActivity>(mContext) {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra("id", it.children[position].id)
                        putExtra("title", it.children[position].name)
                    }
                    return@setOnTagClickListener true
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.0f, 1.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.0f, 1.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = CustomScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1.0f, 0.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1.0f, 0.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = CustomScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }
}