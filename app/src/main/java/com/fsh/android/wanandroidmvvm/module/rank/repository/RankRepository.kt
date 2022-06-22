package com.fsh.android.wanandroidmvvm.module.rank.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.base.repository.ApiRepository
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralHistoryListResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.RankResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class RankRepository(var loadState: MutableLiveData<State>) : com.fsh.android.wanandroidmvvm.base.repository.ApiRepository() {

    fun loadRankList(pageNum: Int, liveData: MutableLiveData<BaseResponse<RankResponse>>) {
        apiService.loadRankList(pageNum)
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

    fun loadMeRankInfo(liveData: MutableLiveData<BaseResponse<IntegralResponse>>) {
        apiService.loadMeRankInfo()
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

    fun loadIntegralHistoryList(pagenum : Int, liveData: MutableLiveData<BaseResponse<IntegralHistoryListResponse>>) {
        apiService.loadIntegralHistory(pagenum)
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