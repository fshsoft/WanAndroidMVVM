package com.fsh.android.wanandroidmvvm.module.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.module.search.model.HotKeyResponse
import com.fsh.android.wanandroidmvvm.module.search.model.SearchResultResponse
import com.fsh.android.wanandroidmvvm.module.search.model.bean.SearchHistory
import com.fsh.android.wanandroidmvvm.module.search.repository.SearchRepostiory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 */
class SearchViewModel : ArticleViewModel<SearchRepostiory>() {
    val mHotKeyData = MutableLiveData<BaseResponse<List<HotKeyResponse>>>()
    val mSearResultData = MutableLiveData<BaseResponse<SearchResultResponse>>()
    val mDeleteHistory = MutableLiveData<Int>()
    val mSearchHistory = MutableLiveData<List<SearchHistory>>()
    val mAddSearchHistory = MutableLiveData<Long>()
    val mClearHistory = MutableLiveData<Int>()

    fun loadHotkey() {
        mRepository.loadHotKey(mHotKeyData)
    }

    fun loadSearchResult(pageNum: Int, key: String) {
        mRepository.loadSearchResult(pageNum, key, mSearResultData)
    }

    fun loadSearchHistory() {
        viewModelScope.launch {
            mSearchHistory.value = withContext(Dispatchers.IO) {
                mRepository.loadSearchHistory()
            }
        }
    }

    fun insertSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            mAddSearchHistory.value = withContext(Dispatchers.IO) {
                mRepository.insertSearchHistory(searchHistory)
            }
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            mDeleteHistory.value = withContext(Dispatchers.IO) {
                mRepository.deleteSearchHistory(searchHistory)
            }
        }
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            mClearHistory.value = withContext(Dispatchers.IO) {
                mRepository.deleteAllSearchHistory()
            }
        }
    }
}
