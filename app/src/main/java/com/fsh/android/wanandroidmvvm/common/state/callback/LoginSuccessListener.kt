package com.fsh.android.wanandroidmvvm.common.state.callback

/**
 * Created with Android Studio.
 * Description:
 */
interface LoginSuccessListener {
    fun loginSuccess(userName : String, userId : String, collectArticleIds : List<Int>?)
}