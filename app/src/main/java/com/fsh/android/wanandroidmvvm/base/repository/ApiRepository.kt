package com.fsh.android.wanandroidmvvm.base.repository

import com.fsh.android.wanandroidmvvm.network.ApiService
import com.fsh.android.wanandroidmvvm.network.RetrofitFactory

/**
 * Created with Android Studio.
 * Description:
 */
abstract class ApiRepository : com.fsh.android.wanandroidmvvm.base.repository.BaseRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}