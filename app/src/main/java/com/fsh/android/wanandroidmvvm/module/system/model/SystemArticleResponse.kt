package com.fsh.android.wanandroidmvvm.module.system.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class SystemArticleResponse(
    var curPage: Int,
    var datas: List<Article>,
    var pageCount: Int,
    var total: Int
)