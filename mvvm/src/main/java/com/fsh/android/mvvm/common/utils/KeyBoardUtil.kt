package com.fsh.android.mvvm.common.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created with Android Studio.
 * Description:
 */
object KeyBoardUtil {
    // 关闭软键盘
    fun Activity.hideKeyboard() {
        // 当前焦点的 View
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}