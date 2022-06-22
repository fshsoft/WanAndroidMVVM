package com.fsh.android.wanandroidmvvm.module.square.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.module.square.model.SquareResponse
import com.fsh.android.wanandroidmvvm.module.square.repository.SquareRepository

/**
 * Created with Android Studio.
 * Description:
 */
class SquareViewModel :
    ArticleViewModel<SquareRepository>() {
    var mSquareData: MutableLiveData<BaseResponse<SquareResponse>> = MutableLiveData()
    var mAddShareData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadSquareArticle(pageNum: Int) {
        mRepository.loadSquareArticle(pageNum, mSquareData)
    }

    fun addShareData(title: String, link: String) {
        mRepository.addShareArticle(title, link, mAddShareData)
    }
}