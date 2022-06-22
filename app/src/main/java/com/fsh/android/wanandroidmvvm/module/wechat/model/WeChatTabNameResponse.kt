package com.fsh.android.wanandroidmvvm.module.wechat.model

/**
 * Created with Android Studio.
 * Description:
 */
data class WeChatTabNameResponse(
    var courseId : Int,
    var name : String,
    var id : Int,
    var order : Int,
    var parentChapterId : Int,
    var userControlSetTop : Boolean
)