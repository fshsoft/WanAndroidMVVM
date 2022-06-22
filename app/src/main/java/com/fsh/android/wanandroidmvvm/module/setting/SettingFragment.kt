package com.fsh.android.wanandroidmvvm.module.setting

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.afollestad.materialdialogs.MaterialDialog
import com.pgyersdk.update.DownloadFileListener
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.common.utils.*
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created with Android Studio.
 * Description:
 */
class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var parentActivity: SettingActivity
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_fragment)
        parentActivity = activity as SettingActivity
        init()
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


    private fun init() {
        var nightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
        val version = "当前版本 " + parentActivity.packageManager.getPackageInfo(
            parentActivity.packageName,
            0
        ).versionName
        findPreference<SwitchPreference>("night")?.isChecked = nightMode
        findPreference<Preference>("version")?.summary = version
        findPreference<Preference>("clearCache")?.summary =
            DataCleanManager.getTotalCacheSize(parentActivity)

        findPreference<SwitchPreference>("night")?.setOnPreferenceChangeListener { preference, newValue ->
            val boolValue = newValue as Boolean
            findPreference<SwitchPreference>("night")?.isChecked = !boolValue
            nightMode = boolValue
            var currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            parentActivity.delegate.localNightMode =
                if (currentMode == Configuration.UI_MODE_NIGHT_NO) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            startActivity<SettingActivity>(parentActivity)
            parentActivity.overridePendingTransition(
                R.anim.animo_alph_open,
                R.anim.animo_alph_close
            )
            parentActivity.finish()
            var nightModeChanged: Boolean by SPreference(Constant.NIGHT_MODE, false)
            AppCompatDelegate.setDefaultNightMode(
                if (nightModeChanged) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            if (nightModeChanged) {
                ColorUtil.setLastColor(ColorUtil.getColor(parentActivity))
                ColorUtil.setColor(
                    ContextCompat.getColor(parentActivity, R.color.colorGray666)
                )
            } else {
                ColorUtil.setColor(ColorUtil.getLastColor(parentActivity))
            }
            true
        }

        // 绑定清理缓存响应事件
        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.title)
                message(text = "确定清除缓存吗？")
                cornerRadius(8.0f)
                positiveButton(text = "清除") {
                    DataCleanManager.clearAllCache(parentActivity)
                    findPreference<Preference>("clearCache")?.summary =
                        DataCleanManager.getTotalCacheSize(parentActivity)
                }
                negativeButton(R.string.cancel)
            }
            false
        }

        findPreference<Preference>("version")?.setOnPreferenceClickListener {
            checkUpdate(parentActivity, true)
            false
        }

        findPreference<Preference>("csdn")?.setOnPreferenceClickListener {
            CommonUtil.startWebView(parentActivity, "https://blog.csdn.net/qq_39424143", "DLUT_fsh")
            false
        }

        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            CommonUtil.startWebView(
                parentActivity,
                "https://github.com/fshsoft/WanAndroidMvvm",
                "WanAndroid"
            )
            false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }
}