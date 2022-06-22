package com.fsh.android.wanandroidmvvm.module.navigation.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.network.dataConvert
import com.fsh.android.wanandroidmvvm.module.navigation.model.NavigationTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class NavigationRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadNavigationTab(liveData: MutableLiveData<BaseResponse<List<NavigationTabNameResponse>>>) {
        apiService.loadNavigationTab()
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

    suspend fun loadNavigationTab(): List<NavigationTabNameResponse> {
        return apiService.loadNavigationTabCo().dataConvert(loadState)
    }
}