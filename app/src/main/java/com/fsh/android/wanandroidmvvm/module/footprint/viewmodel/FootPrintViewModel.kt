package com.fsh.android.wanandroidmvvm.module.footprint.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.state.StateType
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.module.footprint.repository.FootPrintRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 */
class FootPrintViewModel :
    ArticleViewModel<FootPrintRepository>() {

    var mFootPrintData: MutableLiveData<List<Article>> = MutableLiveData()

    fun loadFootPrint() {
        viewModelScope.launch {
            try {
                mFootPrintData.value = withContext(Dispatchers.IO) {
                    mRepository.loadFootPrint()
                }
            } catch (e: Exception) {
                loadState.postValue(State(StateType.ERROR))
            }
        }
    }

    fun deleteFootPrint(article: Article) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    mRepository.deleteFootPrint(article)
                }
            } catch (e: Exception) {
                loadState.postValue(State(StateType.ERROR))
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    mRepository.deleteAll()
                }
                loadFootPrint()
            } catch (e: Exception) {
                loadState.postValue(State(StateType.ERROR))
            }
        }
    }
}