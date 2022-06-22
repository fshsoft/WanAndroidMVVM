package com.fsh.android.wanandroidmvvm.module.system.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.network.dataConvert
import com.fsh.android.wanandroidmvvm.module.system.model.SystemArticleResponse
import com.fsh.android.wanandroidmvvm.module.system.model.SystemTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class SystemRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadSystemTab(liveData: MutableLiveData<BaseResponse<List<SystemTabNameResponse>>>) {
        apiService.loadSystemTab()
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

    fun loadSystemArticle(
        pageNum: Int,
        cid: Int?,
        liveData: MutableLiveData<BaseResponse<SystemArticleResponse>>
    ) {
        apiService.loadSystemArticles(pageNum, cid)
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

    suspend fun loadSystemTabCo(): List<SystemTabNameResponse> {
        return apiService.loadSystemTabCo().dataConvert(loadState)
    }

    suspend fun loadsystemArticleCo(pageNum: Int, cid: Int?): SystemArticleResponse {
        return apiService.loadSystemArticlesCo(pageNum, cid).dataConvert(loadState)
    }
}