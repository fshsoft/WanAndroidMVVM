package com.fsh.android.wanandroidmvvm.common.permission

/**
 * Created with Android Studio.
 * Description: 密封类实现枚举类型
 */
sealed class PermissionResult {
    object Grant : PermissionResult()
    class Deny(val permissions: Array<String>) : PermissionResult()
    class Rationale(val permissions: Array<String>) : PermissionResult()
}