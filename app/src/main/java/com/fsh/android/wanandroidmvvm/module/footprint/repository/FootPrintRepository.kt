package com.fsh.android.wanandroidmvvm.module.footprint.repository

import androidx.lifecycle.MutableLiveData
import com.fsh.android.wanandroidmvvm.common.state.State
import com.fsh.android.wanandroidmvvm.common.utils.RoomHelper
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.module.common.repository.ArticleRepository

/**
 * Created with Android Studio.
 * Description:
 */
class FootPrintRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    suspend fun loadFootPrint() = RoomHelper.queryAllFootPrint(loadState)
    suspend fun deleteFootPrint(article: Article) = RoomHelper.deleteFootPrint(article)
    suspend fun deleteAll() = RoomHelper.deleteAll()
}