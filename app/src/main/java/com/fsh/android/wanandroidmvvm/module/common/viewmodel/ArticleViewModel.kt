package com.fsh.android.wanandroidmvvm.module.common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.fsh.android.wanandroidmvvm.network.initiateRequest
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 */

abstract class ArticleViewModel<T : ArticleRepository> :
    BaseViewModel<T>() {
    // RxJava2
//    var mCollectData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()


//    fun collect(id: Int) {
//        mRepository.collect(id, mCollectData)
//    }

//    fun unCollect(id: Int) {
//        mRepository.unCollect(id, mCollectData)
//    }

    // 使用协程 + Retrofit2.6以上版本
    var mCollectData: MutableLiveData<EmptyResponse> = MutableLiveData()

    fun collectCo(id: Int) {
        initiateRequest({ mCollectData.value = mRepository.collectCo(id) }, loadState)
    }

    fun unCollectCo(id: Int) {
        initiateRequest({ mCollectData.value = mRepository.unCollectCo(id) }, loadState)
    }

    fun addFootPrint(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.insertFootPrint(article)
            }
        }
    }
}