package com.fsh.android.wanandroidmvvm.module.meshare.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class MeShareArticleResponse(
    var curPage :Int,
    var datas : List<Article>
)