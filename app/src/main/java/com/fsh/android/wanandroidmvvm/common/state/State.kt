package com.fsh.android.wanandroidmvvm.common.state

import androidx.annotation.StringRes

/**
 * Created with Android Studio.
 * Description: 状态类
 */
data class State(var code : StateType, var message : String = "", @StringRes var tip : Int = 0)