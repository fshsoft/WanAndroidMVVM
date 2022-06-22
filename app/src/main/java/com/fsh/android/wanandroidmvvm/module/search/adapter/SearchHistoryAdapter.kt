package com.fsh.android.wanandroidmvvm.module.search.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fsh.android.wanandroidmvvm.R

/**
 * Created with Android Studio.
 * Description:
 */
class SearchHistoryAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.search_history_item, null) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.run {
            setText(R.id.history_name, item ?: "")
            addOnClickListener(R.id.history_delete)
        }
    }
}