package com.fsh.android.wanandroidmvvm.module.meshare.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.module.meshare.model.MeShareArticleResponse
import com.fsh.android.wanandroidmvvm.module.meshare.model.MeShareResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class MeShareRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadShareArticle(pageNum : Int, liveData : MutableLiveData<BaseResponse<MeShareResponse<MeShareArticleResponse>>>) {
        apiService.loadMeShareArticle(pageNum)
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

    fun deleteShareArticle(id: Int, liveData : MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.deleteShareArticle(id)
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