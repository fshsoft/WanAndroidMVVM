package com.fsh.android.wanandroidmvvm.module.square.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class SquareResponse(
    var curPage : Int,
    var datas : List<Article>
)