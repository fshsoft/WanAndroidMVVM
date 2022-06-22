package com.fsh.android.wanandroidmvvm.module.search.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with Android Studio.
 * Description:
 */
@Entity
data class SearchHistory(
    var name : String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var primaryKeyId: Int = 0
}