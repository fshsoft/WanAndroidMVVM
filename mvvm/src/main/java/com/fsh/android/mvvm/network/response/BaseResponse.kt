package com.fsh.android.mvvm.network.response

/**
 * Created with Android Studio.
 * Description: 返回数据基类
 */

open class BaseResponse<T>(var data: T, var errorCode: Int = -1, var errorMsg: String = "")