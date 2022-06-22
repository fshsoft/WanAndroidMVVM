package com.fsh.android.mvvm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.fsh.android.mvvm.common.utils.Constant
import com.fsh.android.mvvm.common.utils.SPreference

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
    }
}