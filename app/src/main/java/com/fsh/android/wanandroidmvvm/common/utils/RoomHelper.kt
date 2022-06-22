package com.fsh.android.wanandroidmvvm.common.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.view.BaseApplication
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.state.StateType
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.module.footprint.model.database.FootPrintDataBase
import com.fsh.android.wanandroidmvvm.module.search.model.bean.SearchHistory
import com.fsh.android.wanandroidmvvm.module.search.model.db.SearchHistoryDataBase

/**
 * Created with Android Studio.
 * Description:
 */
object RoomHelper {
    /**
     * 足迹RoomHelper
     */
    private val footPrintDataBase by lazy {
        FootPrintDataBase.getInstance(BaseApplication.instance)
    }

    private val footPrintDao by lazy {
        footPrintDataBase?.footPrintDao()
    }

    // 按最近顺序组织足迹列表
    suspend fun queryAllFootPrint(loadState: MutableLiveData<State>): List<Article> {
        val response = footPrintDao?.queryAllFootPrint()?.reversed()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.EMPTY))
        }
        return response
    }

    suspend fun insertFootPrint(article: Article) {
        footPrintDao?.let {
            it.queryArticleById(article.id)?.let {
                var i = footPrintDao!!.deleteArticle(it)
                Log.e("DELETE", i.toString())
            }
            it.insertFootPrint(article.apply { primaryKeyId = 0 })
        }
    }

    suspend fun deleteFootPrint(article: Article) {
        var i = footPrintDao?.deleteArticle(article)
        Log.e("DELETE", i.toString())
    }

    suspend fun deleteAll() {
        footPrintDao?.deleteAll()
    }

    /**
     * 搜索历史RoomHelper
     */
    private val searchHistoryDataBase by lazy {
        SearchHistoryDataBase.getInstance(BaseApplication.instance)
    }

    private val searchHistoryDao by lazy {
        searchHistoryDataBase?.searchHistoryDao()
    }

    suspend fun queryAllSearchHistory(): List<SearchHistory> {
        return searchHistoryDao?.queryAllSearchHistory()?.reversed()!!
    }

    suspend fun insertSearchHistory(searchHistory: SearchHistory): Long {
        var result: Long = 0
        searchHistoryDao?.let {
            var history = it.queryAllSearchHistory()
            it.deleteSearchHistory(history.filter { history ->
                return@filter history.name == searchHistory.name
            }.getOrElse(0) {
                return@getOrElse if (history.size >= 10) history[9] else SearchHistory()
            })
            result = it.insertSearchHistory(searchHistory)
        }
        return result
    }

    suspend fun deleteSearchHistory(searchHistory: SearchHistory): Int? {
        return searchHistoryDao?.deleteSearchHistory(searchHistory)
    }

    suspend fun deleteAllSearchHistory(): Int? {
        return searchHistoryDao?.deleteAllSearchHistory()
    }
}

