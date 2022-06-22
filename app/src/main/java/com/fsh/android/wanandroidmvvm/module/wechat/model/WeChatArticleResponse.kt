package com.fsh.android.wanandroidmvvm.module.wechat.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class WeChatArticleResponse(
    var curPage: Int,
    var datas: List<Article>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)