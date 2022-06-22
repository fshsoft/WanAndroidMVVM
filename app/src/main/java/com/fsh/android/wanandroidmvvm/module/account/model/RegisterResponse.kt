package com.fsh.android.wanandroidmvvm.module.account.model

/**
 * Created with Android Studio.
 * Description:
 */
data class RegisterResponse(
    var username : String,
    var id : Int,
    var icon : String,
    var type : Int,
    var collectIds : List<Int>
)