package com.fsh.android.mvvm.network

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParseException
import com.fsh.android.mvvm.common.state.State
import com.fsh.android.mvvm.common.state.StateType
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.Exception

/**
 * Created with Android Studio.
 * Description:
 */
object NetExceptionHandle {
    fun handleException(e: Throwable?, loadState: MutableLiveData<State>){
        val ex = Exception()
        e?.let {
            when (it) {
                is HttpException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR))
                }
                is ConnectException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR))
                }
                is ConnectTimeoutException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR))
                }
                is UnknownHostException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR))
                }
                is JsonParseException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR))
                }
            }
        }
    }
}