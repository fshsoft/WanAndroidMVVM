package com.fsh.android.wanandroidmvvm.module.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.fsh.android.wanandroidmvvm.network.initiateRequest
import com.fsh.android.wanandroidmvvm.module.navigation.model.NavigationTabNameResponse
import com.fsh.android.wanandroidmvvm.module.navigation.repository.NavigationRepository

/**
 * Created with Android Studio.
 * Description:
 */
class NavigationViewModel :
    BaseViewModel<NavigationRepository>() {
//    val mNavigationTabData : MutableLiveData<BaseResponse<List<NavigationTabNameResponse>>> = MutableLiveData()
//
//    fun loadNavigationTab() {
//        mRepository.loadNavigationTab(mNavigationTabData)
//    }

    val mNavigationTabData: MutableLiveData<List<NavigationTabNameResponse>> = MutableLiveData()

    fun loadNavigationTab() {
        initiateRequest({mNavigationTabData.value = mRepository.loadNavigationTab()},loadState)
    }
}