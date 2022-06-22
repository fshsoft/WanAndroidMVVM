package com.fsh.android.wanandroidmvvm.module.question.questionrepository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.fsh.android.wanandroidmvvm.base.observer.BaseObserver
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.module.question.model.QuestionResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 */
class QuestionRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadQuestionList(pageNum : Int, liveData : MutableLiveData<BaseResponse<QuestionResponse>>) {
        apiService.loadQuestionList(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                com.fsh.android.wanandroidmvvm.base.observer.BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }
}