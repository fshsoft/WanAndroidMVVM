package com.fsh.android.wanandroidmvvm.module.account.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.base.repository.ApiRepository
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.network.dataConvert
import com.fsh.android.wanandroidmvvm.module.account.model.LoginResponse
import com.fsh.android.wanandroidmvvm.module.account.model.RegisterResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class AccountRepository(val loadState: MutableLiveData<State>) : com.fsh.android.wanandroidmvvm.base.repository.ApiRepository() {
    fun login(
        username: String,
        password: String,
        liveData: MutableLiveData<BaseResponse<LoginResponse>>
    ) {
        apiService.onLogin(username, password)
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

    fun register(
        username: String,
        password: String,
        repassword: String,
        liveData: MutableLiveData<BaseResponse<RegisterResponse>>
    ) {
        apiService.onRegister(username, password, repassword)
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

    // 使用协程 + Retrofit2.6
    suspend fun loginCo(username: String, password: String): LoginResponse {
        return apiService.onLoginCo(username, password).dataConvert(loadState)
    }

    suspend fun registerCo(
        username: String,
        password: String,
        repassword: String
    ): RegisterResponse {
        return apiService.onRegisterCo(username, password, repassword).dataConvert(loadState)
    }
}