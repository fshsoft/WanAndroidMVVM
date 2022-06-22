package com.fsh.android.wanandroidmvvm.module.project.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class ProjectResponse(
    var datas : List<Article>,
    var curPage : Int,
    var size : Int,
    var total : Int,
    var pageCount : Int
)