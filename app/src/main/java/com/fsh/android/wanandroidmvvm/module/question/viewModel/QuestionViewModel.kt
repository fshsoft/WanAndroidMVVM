package com.fsh.android.wanandroidmvvm.module.question.viewModel

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.module.question.questionrepository.QuestionRepository
import com.fsh.android.wanandroidmvvm.module.question.model.QuestionResponse

/**
 * Created with Android Studio.
 * Description:
 */

class QuestionViewModel :
    ArticleViewModel<QuestionRepository>() {
    var mQuestionData: MutableLiveData<BaseResponse<QuestionResponse>> = MutableLiveData()

    fun loadQuestionList(pageNum: Int) {
        mRepository.loadQuestionList(pageNum, mQuestionData)
    }
}