package com.fsh.android.wanandroidmvvm.module.rank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralHistoryListResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.RankResponse
import com.fsh.android.wanandroidmvvm.module.rank.repository.RankRepository

/**
 * Created with Android Studio.
 * Description:
 */
class RankViewModel : BaseViewModel<RankRepository>() {
    val mRankListData: MutableLiveData<BaseResponse<RankResponse>> = MutableLiveData()
    val mMeRankInfo: MutableLiveData<BaseResponse<IntegralResponse>> = MutableLiveData()
    val mIntegralHistoryListData: MutableLiveData<BaseResponse<IntegralHistoryListResponse>> =
        MutableLiveData()

    fun loadRankList(pageNum: Int) {
        mRepository.loadRankList(pageNum, mRankListData)
    }

    fun loadMeRankInfo() {
        mRepository.loadMeRankInfo(mMeRankInfo)
    }

    fun loadIntegralHistoryList(pageNum: Int) {
        mRepository.loadIntegralHistoryList(pageNum, mIntegralHistoryListData)
    }
}