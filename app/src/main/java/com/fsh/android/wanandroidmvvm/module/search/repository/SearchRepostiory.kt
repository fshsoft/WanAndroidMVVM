package com.fsh.android.wanandroidmvvm.module.search.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.utils.RoomHelper
import com.fsh.android.wanandroidmvvm.module.search.model.HotKeyResponse
import com.fsh.android.wanandroidmvvm.module.search.model.SearchResultResponse
import com.fsh.android.wanandroidmvvm.module.search.model.bean.SearchHistory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class SearchRepostiory(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadHotKey(liveData: MutableLiveData<BaseResponse<List<HotKeyResponse>>>) {
        apiService.loadHotKey()
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

    fun loadSearchResult(
        pageNum: Int,
        key: String,
        liveData: MutableLiveData<BaseResponse<SearchResultResponse>>
    ) {
        apiService.loadSearchResult(pageNum, key)
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

    suspend fun loadSearchHistory() = RoomHelper.queryAllSearchHistory()

    suspend fun insertSearchHistory(searchHistory: SearchHistory) =
        RoomHelper.insertSearchHistory(searchHistory)

    suspend fun deleteSearchHistory(searchHistory: SearchHistory) =
        RoomHelper.deleteSearchHistory(searchHistory)

    suspend fun deleteAllSearchHistory() = RoomHelper.deleteAllSearchHistory()
}