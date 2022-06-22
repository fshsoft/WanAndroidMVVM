package com.fsh.android.wanandroidmvvm.common.state

import android.content.Context
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.common.state.callback.CollectListener
import com.fsh.android.wanandroidmvvm.common.utils.startActivity
import com.fsh.android.wanandroidmvvm.module.account.view.LoginActivity
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 */
class LogoutState :UserState {
    override fun collect(context: Context, position: Int, listener: CollectListener) {
        startLoginActivity(context)
    }

    override fun login(context: Context) {
        startLoginActivity(context)
    }

    override fun startRankActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startCollectActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startShareActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startAddShareActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startTodoActivity(context: Context) {
        startLoginActivity(context)
    }

    private fun startLoginActivity(context: Context) {
        context?.let {
            it.toast(it.getString(R.string.please_login))
            startActivity<LoginActivity>(it)
        }
    }

    override fun startEditTodoActivity(context: Context) {
        startLoginActivity(context)
    }
}