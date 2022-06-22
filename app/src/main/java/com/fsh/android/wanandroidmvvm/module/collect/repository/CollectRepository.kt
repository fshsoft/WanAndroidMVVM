package com.fsh.android.wanandroidmvvm.module.collect.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.module.collect.model.CollectResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class CollectRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {

    fun loadCollectArticle(pageNum: Int, liveData: MutableLiveData<BaseResponse<CollectResponse>>) {
        apiService.loadCollectArticle(pageNum)
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

    fun addCollectArticle(
        title: String,
        author: String,
        link: String,
        liveData: MutableLiveData<BaseResponse<EmptyResponse>>
    ) {
        apiService.addCollectArticle(title, author, link)
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

    fun unCollect(id: Int, originId: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.unCollect(id, originId)
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