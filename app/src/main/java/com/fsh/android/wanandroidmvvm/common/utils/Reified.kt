package com.fsh.android.wanandroidmvvm.common.utils

import android.content.Context
import android.content.Intent

/**
 * Created with Android Studio.
 * Description:
 */

inline fun <reified T> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}