package com.fsh.android.wanandroidmvvm.module.collect.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.module.collect.model.CollectResponse
import com.fsh.android.wanandroidmvvm.module.collect.repository.CollectRepository

/**
 * Created with Android Studio.
 * Description:
 */
class CollectViewModel :
    ArticleViewModel<CollectRepository>() {
    val mCollectArticleData: MutableLiveData<BaseResponse<CollectResponse>> = MutableLiveData()
    val mAddCollectData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mUnCollectData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadCollectArticle(pageNum: Int) {
        mRepository.loadCollectArticle(pageNum, mCollectArticleData)
    }

    fun addCollectArticle(title: String, author: String, link: String) {
        mRepository.addCollectArticle(title, author, link, mAddCollectData)
    }

    fun unCollect(id: Int, originId: Int) {
        mRepository.unCollect(id, originId, mUnCollectData)
    }
}