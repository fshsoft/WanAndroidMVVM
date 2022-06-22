package com.fsh.android.wanandroidmvvm.module.todo.model

/**
 * Created with Android Studio.
 * Description:
 */
data class TodoPageResponse(
    var curPage: Int,
    var datas: List<TodoResponse>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)