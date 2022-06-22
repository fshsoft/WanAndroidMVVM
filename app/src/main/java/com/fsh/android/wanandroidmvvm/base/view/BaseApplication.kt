package com.fsh.android.wanandroidmvvm.base.view

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import com.fsh.android.wanandroidmvvm.common.callback.EmptyCallBack
import com.fsh.android.wanandroidmvvm.common.callback.ErrorCallBack
import com.fsh.android.wanandroidmvvm.common.callback.LoadingCallBack
import com.fsh.android.wanandroidmvvm.common.utils.Constant
import com.fsh.android.wanandroidmvvm.common.utils.SPreference

/**
 * Created with Android Studio.
 * Description:
 */
open class BaseApplication : Application() {
    companion object {
        lateinit var instance : BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        SPreference.setContext(applicationContext)
        initMode()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
    }

    private fun initMode() {
        var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}