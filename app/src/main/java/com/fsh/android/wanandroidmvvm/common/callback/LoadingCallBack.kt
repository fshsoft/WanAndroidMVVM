package com.fsh.android.wanandroidmvvm.common.callback

import com.kingja.loadsir.callback.Callback
import com.fsh.android.wanandroidmvvm.R

/**
 * Created with Android Studio.
 * Description:
 */
class LoadingCallBack : Callback() {
    override fun onCreateView(): Int = R.layout.layout_loading
}