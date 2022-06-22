package com.fsh.android.wanandroidmvvm.module.todo.model

/**
 * Created with Android Studio.
 * Description:
 */
data class TodoResponse(
    var completeDate: String,
    var content: String,
    var date : Long,
    var dateStr: String,
    var id: Int,
    var priority: Int,
    var status: Int,
    var type: Int,
    var title: String
)