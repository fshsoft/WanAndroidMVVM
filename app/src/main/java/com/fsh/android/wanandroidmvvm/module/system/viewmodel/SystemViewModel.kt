package com.fsh.android.wanandroidmvvm.module.system.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.network.initiateRequest
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.module.system.model.SystemArticleResponse
import com.fsh.android.wanandroidmvvm.module.system.model.SystemTabNameResponse
import com.fsh.android.wanandroidmvvm.module.system.repository.SystemRepository

/**
 * Created with Android Studio.
 * Description:
 */
class SystemViewModel : ArticleViewModel<SystemRepository>() {
//    val mSystemTabNameData : MutableLiveData<BaseResponse<List<SystemTabNameResponse>>> = MutableLiveData()
//    val mSystemArticleData : MutableLiveData<BaseResponse<SystemArticleResponse>> = MutableLiveData()
//
//    fun loadSystemTab() {
//        mRepository.loadSystemTab(mSystemTabNameData)
//    }
//
//    fun loadSystemArticle(pageNum : Int, cid : Int?) {
//        mRepository.loadSystemArticle(pageNum, cid, mSystemArticleData)
//    }

    val mSystemTabNameData: MutableLiveData<List<SystemTabNameResponse>> = MutableLiveData()
    val mSystemArticleData: MutableLiveData<SystemArticleResponse> = MutableLiveData()

    fun loadSystemTab() {
        initiateRequest({ mSystemTabNameData.value = mRepository.loadSystemTabCo() }, loadState)
    }

    fun loadSystemArticle(pageNum: Int, cid: Int?) {
        initiateRequest({
            mSystemArticleData.value = mRepository.loadsystemArticleCo(pageNum, cid)
        }, loadState)
    }
}