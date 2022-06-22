package com.fsh.android.wanandroidmvvm.module.footprint.model.dao

import androidx.room.*
import com.fsh.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 */
@Dao
interface FootPrintDao {
    @Transaction
    @Insert(entity = Article::class)
    suspend fun insertFootPrint(article: Article): Long

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun queryAllFootPrint(): List<Article>

    @Transaction
    @Query("SELECT * FROM article WHERE id = (:id)")
    suspend fun queryArticleById(id: Int): Article?

    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article) : Int

    @Transaction
    @Query("DELETE FROM article")
    suspend fun deleteAll()
}