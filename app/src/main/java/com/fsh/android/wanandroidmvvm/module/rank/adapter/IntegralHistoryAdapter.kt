package com.fsh.android.wanandroidmvvm.module.rank.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.DateUtil
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralHistoryResponse
import kotlinx.android.synthetic.main.integral_history_item.view.*

/**
 * Created with Android Studio.
 * Description:
 */
class IntegralHistoryAdapter(layoutId: Int, listData: MutableList<IntegralHistoryResponse>?) :
    BaseQuickAdapter<IntegralHistoryResponse, BaseViewHolder>(layoutId, listData) {
    override fun convert(viewHolder: BaseViewHolder, item: IntegralHistoryResponse?) {
        viewHolder?.let { holder ->
            holder.itemView.integral_material_card.rippleColor = ColorUtil.getOneColorStateList(mContext)
            holder.itemView.integral_material_card.strokeColor = ColorUtil.getColor(mContext)
            val descStr = if (item!!.desc.contains("积分")) item.desc.subSequence(
                item.desc.indexOf("积分"),
                item.desc.length
            ) else ""
            holder.setText(R.id.item_integralhistory_title, item.reason +"" + descStr)
            holder.setText(R.id.item_integralhistory_date, DateUtil.formatDate(item.date))
            holder.setText(R.id.item_integralhistory_count, "+(" + item.coinCount+ ")")
            holder.setTextColor(R.id.item_integralhistory_count, ColorUtil.getColor(mContext))
        }
    }
}
