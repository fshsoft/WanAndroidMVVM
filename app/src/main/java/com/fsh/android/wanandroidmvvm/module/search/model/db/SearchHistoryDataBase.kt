package com.fsh.android.wanandroidmvvm.module.search.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fsh.android.wanandroidmvvm.module.search.model.bean.SearchHistory
import com.fsh.android.wanandroidmvvm.module.search.model.dao.SearchHistoryDao

/**
 * Created with Android Studio.
 * Description:
 */
@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class SearchHistoryDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private var INSTANCE: SearchHistoryDataBase? = null
        fun getInstance(context: Context): SearchHistoryDataBase? {
            if (INSTANCE == null) {
                synchronized(SearchHistoryDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            SearchHistoryDataBase::class.java,
                            "database_search_history"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}