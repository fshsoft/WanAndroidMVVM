package com.fsh.android.wanandroidmvvm.module.collect.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class CollectResponse(
    var curPage : Int,
    var datas : List<Article>,
    var total : Int
)