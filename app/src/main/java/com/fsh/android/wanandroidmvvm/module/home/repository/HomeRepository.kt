package com.fsh.android.wanandroidmvvm.module.home.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.network.dataConvert
import com.fsh.android.wanandroidmvvm.module.home.model.BannerResponse
import com.fsh.android.wanandroidmvvm.module.home.model.HomeArticleResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class HomeRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadBanner(liveData: MutableLiveData<BaseResponse<List<BannerResponse>>>) {
        apiService.loadBanner()
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

    fun loadHomeArticle(
        pageNum: Int,
        liveData: MutableLiveData<BaseResponse<HomeArticleResponse>>
    ) {
        apiService.loadHomeArticle(pageNum)
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

    fun loadTopArticle(liveData: MutableLiveData<BaseResponse<List<Article>>>) {
        apiService.loadTopArticle()
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
    suspend fun loadBannerCo(): List<BannerResponse> {
        return apiService.loadBannerCo().dataConvert(loadState)
    }

    suspend fun loadTopArticleCo(): List<Article> {
        return apiService.loadTopArticleCo().dataConvert(loadState)
    }

    suspend fun loadHomeArticleCo(pageNum: Int): HomeArticleResponse {
        return apiService.loadHomeArticleCo(pageNum).dataConvert(loadState)
    }
}