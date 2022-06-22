package com.fsh.android.wanandroidmvvm.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsh.android.wanandroidmvvm.base.repository.BaseRepository
import com.fsh.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.state.StateType
import com.fsh.android.wanandroidmvvm.common.state.UserInfo
import com.fsh.android.wanandroidmvvm.common.utils.Constant
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:数据解析扩展函数
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>
): T {
    when (errorCode) {
        Constant.SUCCESS -> {
            if (data is List<*>) {
                if ((data as List<*>).isEmpty()) {
                    loadState.postValue(State(StateType.EMPTY))
                }
            }
            loadState.postValue(State(StateType.SUCCESS))
            return data
        }
        Constant.NOT_LOGIN -> {
            UserInfo.instance.logoutSuccess()
            loadState.postValue(State(StateType.ERROR, message = "请重新登录"))
            return data
        }
        else -> {
            loadState.postValue(State(StateType.ERROR, message = errorMsg))
            return data
        }
    }
}


fun <T : BaseRepository> BaseViewModel<T>.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}
