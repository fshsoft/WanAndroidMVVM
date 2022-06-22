package com.fsh.android.wanandroidmvvm.module.todo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.module.todo.model.TodoPageResponse
import com.fsh.android.wanandroidmvvm.module.todo.repository.TodoRepository

/**
 * Created with Android Studio.
 * Description:
 */
class TodoViewModel : BaseViewModel<TodoRepository>() {
    val mTodoListData: MutableLiveData<BaseResponse<TodoPageResponse>> = MutableLiveData()
    val mTodoAddData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mTodoUpdateData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mTodoFinishData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mTodoDeleteData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadTodoList(pageNum: Int = 1) {
        mRepository.loadTodoList(pageNum, mTodoListData)
    }

    fun addTodo(title: String, content: String, date: String, type: Int, priority: Int) {
        mRepository.addTodo(title, content, date, type, priority, mTodoAddData)
    }

    fun finishTodo(id: Int, status: Int) {
        mRepository.finishTodo(id, status, mTodoFinishData)
    }

    fun updateTodo(
        id: Int?,
        title: String,
        content: String,
        date: String,
        type: Int,
        priority: Int
    ) {
        mRepository.updateTodo(id, title, content, date, type, priority, mTodoUpdateData)
    }

    fun deleteTodo(id: Int) {
        mRepository.deleteTodo(id, mTodoDeleteData)
    }
}