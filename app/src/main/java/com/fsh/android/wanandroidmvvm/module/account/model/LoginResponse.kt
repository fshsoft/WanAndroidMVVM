package com.fsh.android.wanandroidmvvm.module.account.model

/**
 * Created with Android Studio.
 * Description:
 */
data class LoginResponse(
    var icon : String,
    var type : String,
    var id : Int,
    var collectIds : List<Int>,
    var username : String
)