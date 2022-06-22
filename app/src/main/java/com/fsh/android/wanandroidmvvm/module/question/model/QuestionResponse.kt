package com.fsh.android.wanandroidmvvm.module.question.model

import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
data class QuestionResponse(
    var curPage : Int,
    var datas : List<Article>
)