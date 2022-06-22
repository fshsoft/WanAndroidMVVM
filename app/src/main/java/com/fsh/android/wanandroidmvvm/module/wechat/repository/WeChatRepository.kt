package com.fsh.android.wanandroidmvvm.module.wechat.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.network.dataConvert
import com.fsh.android.wanandroidmvvm.module.wechat.model.WeChatArticleResponse
import com.fsh.android.wanandroidmvvm.module.wechat.model.WeChatTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class WeChatRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadWeChatTabName(liveData: MutableLiveData<BaseResponse<List<WeChatTabNameResponse>>>) {
        apiService.loadWeChatTab()
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

    fun loadWeChatArticle(
        cid: Int,
        pageNum: Int,
        liveData: MutableLiveData<BaseResponse<WeChatArticleResponse>>
    ) {
        apiService.loadWeChatArticles(cid, pageNum)
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

    suspend fun loadWeChatTabNameCo(): List<WeChatTabNameResponse> {
        return apiService.loadWeChatTabCo().dataConvert(loadState)
    }

    suspend fun loadWeChatArticleCo(cid: Int, pageNum: Int): WeChatArticleResponse {
        return apiService.loadWeChatArticlesCo(cid, pageNum).dataConvert(loadState)
    }
}