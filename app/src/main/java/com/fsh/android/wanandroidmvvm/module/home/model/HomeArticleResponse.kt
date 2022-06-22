package com.fsh.android.wanandroidmvvm.module.home.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class HomeArticleResponse(var curPage: Int,
                               var datas: List<Article>,
                               var offset: Int,
                               var over: Boolean,
                               var pageCount: Int,
                               var size: Int,
                               var total: Int)