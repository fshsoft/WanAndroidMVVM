package com.fsh.android.wanandroidmvvm.module.common.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.base.repository.ApiRepository
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.utils.RoomHelper
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.network.dataConvert
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 */
abstract class ArticleRepository(val loadState: MutableLiveData<State>) : com.fsh.android.wanandroidmvvm.base.repository.ApiRepository() {

    fun collect(id: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.collect(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun unCollect(id: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.unCollect(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    suspend fun collectCo(id : Int ) :EmptyResponse {
        return withContext(Dispatchers.IO) {
            apiService.collectCo(id).dataConvert(loadState)
        }
    }

    suspend fun unCollectCo(id : Int ) :EmptyResponse {
        return withContext(Dispatchers.IO) {
            apiService.unCollectCo(id).dataConvert(loadState)
        }
    }

    suspend fun insertFootPrint(article: Article) = RoomHelper.insertFootPrint(article)
}