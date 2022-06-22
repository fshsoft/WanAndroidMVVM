package com.fsh.android.wanandroidmvvm.common.state

import android.content.Context
import com.fsh.android.wanandroidmvvm.common.state.callback.CollectListener

/**
 * Created with Android Studio.
 * Description:
 */
interface UserState {
    fun collect(context: Context, position: Int, listener: CollectListener)

    fun login(context: Context)

    fun startRankActivity(context: Context)

    fun startTodoActivity(context: Context)

    fun startCollectActivity(context: Context)

    fun startShareActivity(context: Context)

    fun startAddShareActivity(context: Context)

    fun startEditTodoActivity(context: Context)
}