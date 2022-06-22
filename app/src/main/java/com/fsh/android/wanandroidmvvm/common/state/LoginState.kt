package com.fsh.android.wanandroidmvvm.common.state

import android.content.Context
import com.fsh.android.wanandroidmvvm.common.state.callback.CollectListener
import com.fsh.android.wanandroidmvvm.common.utils.Constant
import com.fsh.android.wanandroidmvvm.common.utils.startActivity
import com.fsh.android.wanandroidmvvm.module.collect.view.CollectArticleListActivity
import com.fsh.android.wanandroidmvvm.module.meshare.view.MeShareActivity
import com.fsh.android.wanandroidmvvm.module.rank.view.RankActivity
import com.fsh.android.wanandroidmvvm.module.square.view.ShareArticleActivity
import com.fsh.android.wanandroidmvvm.module.todo.view.EditTodoActivity
import com.fsh.android.wanandroidmvvm.module.todo.view.TodoActivity

/**
 * Created with Android Studio.
 * Description:
 */
class LoginState : UserState {

    override fun collect(context: Context, position: Int, listener: CollectListener) {
        listener.collect(position)
    }

    override fun login(context: Context) {}

    override fun startRankActivity(context: Context) {
        startActivity<RankActivity>(context)
    }

    override fun startCollectActivity(context: Context) {
        startActivity<CollectArticleListActivity>(context)
    }

    override fun startShareActivity(context: Context) {
        startActivity<MeShareActivity>(context)
    }

    override fun startAddShareActivity(context: Context) {
        startActivity<ShareArticleActivity>(context)
    }

    override fun startTodoActivity(context: Context) {
        startActivity<TodoActivity>(context)
    }

    override fun startEditTodoActivity(context: Context) {
        startActivity<EditTodoActivity>(context) {
            putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.ADD_TODO)
        }
    }
}