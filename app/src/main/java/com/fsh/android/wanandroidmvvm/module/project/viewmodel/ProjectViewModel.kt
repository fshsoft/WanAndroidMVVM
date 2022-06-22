package com.fsh.android.wanandroidmvvm.module.project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.network.initiateRequest
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.module.project.model.ProjectResponse
import com.fsh.android.wanandroidmvvm.module.project.model.ProjectTabResponse
import com.fsh.android.wanandroidmvvm.module.project.repository.ProjectRespository

/**
 * Created with Android Studio.
 * Description:
 */
class ProjectViewModel :
    ArticleViewModel<ProjectRespository>() {
//    val mProjectTabData : MutableLiveData<BaseResponse<List<ProjectTabResponse>>> = MutableLiveData()
//    val mProjectArticleData : MutableLiveData<BaseResponse<ProjectResponse>> = MutableLiveData()
//
//    fun loadProjectTab() {
//        mRepository.loadProjectTab(mProjectTabData)
//    }
//
//    fun loadProjectArticle(pageNum : Int, cid : Int) {
//        mRepository.loadProjectArticle(pageNum, cid, mProjectArticleData)
//    }

    val mProjectTabData: MutableLiveData<List<ProjectTabResponse>> = MutableLiveData()
    val mProjectArticleData: MutableLiveData<ProjectResponse> = MutableLiveData()

    fun loadProjectTab() {
        initiateRequest({ mProjectTabData.value = mRepository.loadProjectTabCo() }, loadState)
    }

    fun loadProjectArticle(pageNum: Int, cid: Int) {
        initiateRequest({
            mProjectArticleData.value = mRepository.loadProjectArticleCo(pageNum, cid)
        }, loadState)
    }
}