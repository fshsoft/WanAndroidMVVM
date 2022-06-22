package com.fsh.android.wanandroidmvvm.module.todo.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.base.repository.ApiRepository
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.module.todo.model.TodoPageResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class TodoRepository(var loadState: MutableLiveData<State>) : com.fsh.android.wanandroidmvvm.base.repository.ApiRepository() {

    fun loadTodoList(pageNum: Int, liveData: MutableLiveData<BaseResponse<TodoPageResponse>>) {
        apiService.loadTodoData(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun addTodo(
        title: String, content: String, date: String, type: Int, priority: Int,
        liveData: MutableLiveData<BaseResponse<EmptyResponse>>
    ) {
        apiService.addTodo(title, content, date, type, priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun finishTodo(id: Int, status: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.finishTodo(id, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun updateTodo(
        id: Int?, title: String, content: String, date: String, type: Int, priority: Int,
        liveData: MutableLiveData<BaseResponse<EmptyResponse>>
    ) {
        apiService.updateTodo(id, title, content, date, type, priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun deleteTodo(id: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.deleteTodo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }
}