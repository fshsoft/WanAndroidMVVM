package com.fsh.android.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fsh.android.mvvm.repository.BaseRepository
import com.fsh.android.mvvm.common.*
import com.fsh.android.mvvm.common.state.State
import com.fsh.android.mvvm.common.utils.CommonUtil

/**
 * Created with Android Studio.
 * Description:
 */
open class BaseViewModel<T : BaseRepository>(application: Application) : AndroidViewModel(
    application
) {
    val loadState by lazy {
        MutableLiveData<State>()
    }

    val mRepository : T by lazy {
        (CommonUtil.getClass<T>(this))
            .getDeclaredConstructor(MutableLiveData::class.java)
            .newInstance(loadState)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.unSubscribe()
    }
}