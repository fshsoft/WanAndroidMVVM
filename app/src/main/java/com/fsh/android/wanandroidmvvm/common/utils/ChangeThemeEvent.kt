package com.fsh.android.wanandroidmvvm.common.utils

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 */
class ChangeThemeEvent {
    fun post() {
        EventBus.getDefault().post(this)
    }
}