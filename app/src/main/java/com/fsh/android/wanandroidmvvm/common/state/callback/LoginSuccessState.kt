package com.fsh.android.wanandroidmvvm.common.state.callback

/**
 * Created with Android Studio.
 * Description:
 */

object LoginSuccessState {
    var listeners = ArrayList<LoginSuccessListener>()

    fun addListener(listener : LoginSuccessListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: LoginSuccessListener) {
        listeners.remove(listener)
    }

    fun notifyLoginState(name : String, userId : String, collectIds : List<Int>?) {
        for (listener in listeners) {
            listener.loginSuccess(name, userId, collectIds)
        }
    }
}