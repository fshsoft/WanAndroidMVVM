package com.fsh.android.wanandroidmvvm.module.meshare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.module.meshare.model.MeShareArticleResponse
import com.fsh.android.wanandroidmvvm.module.meshare.model.MeShareResponse
import com.fsh.android.wanandroidmvvm.module.meshare.repository.MeShareRepository

/**
 * Created with Android Studio.
 * Description:
 */
class MeShareViewModel : ArticleViewModel<MeShareRepository>() {
    var mMeShareData: MutableLiveData<BaseResponse<MeShareResponse<MeShareArticleResponse>>> =
        MutableLiveData()
    var mDeleteMeShareData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadMeShareArticle(pageNum: Int) {
        mRepository.loadShareArticle(pageNum, mMeShareData)
    }

    fun deleteMeShareArticle(id: Int) {
        mRepository.deleteShareArticle(id, mDeleteMeShareData)
    }
}