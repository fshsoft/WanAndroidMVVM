package com.fsh.android.wanandroidmvvm.base.observer

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.base.repository.BaseRepository
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.state.StateType
import com.fsh.android.wanandroidmvvm.common.state.UserInfo
import com.fsh.android.wanandroidmvvm.common.utils.Constant
import io.reactivex.disposables.Disposable

/**
 * Created with Android Studio.
 * Description:
 */

class BaseObserver<T : BaseResponse<*>>(
    val liveData: MutableLiveData<T>,
    val loadState: MutableLiveData<State>,
    val repository: BaseRepository
) : Observer<T> {
    override fun onNext(response: T) {
        when (response.errorCode) {
            Constant.SUCCESS -> {
                if (response.data is List<*>) {
                    if ((response.data as List<*>).isEmpty()) {
                        loadState.postValue(State(StateType.EMPTY))
                        return
                    }
                }
                loadState.postValue(State(StateType.SUCCESS))
                liveData.postValue(response)
            }
            Constant.NOT_LOGIN -> {
                UserInfo.instance.logoutSuccess()
                loadState.postValue(State(StateType.ERROR, message = "请重新登录"))
            }
            else -> {
                loadState.postValue(State(StateType.ERROR, message = response.errorMsg))
            }
        }
    }

    override fun onSubscribe(disposable: Disposable) {
        repository.addSubscribe(disposable)
    }

    override fun onError(p0: Throwable) {
        loadState.postValue(State(StateType.NETWORK_ERROR))
    }

    override fun onComplete() {}
}