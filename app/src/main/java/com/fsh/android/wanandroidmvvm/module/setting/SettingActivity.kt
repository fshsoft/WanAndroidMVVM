package com.fsh.android.wanandroidmvvm.module.setting

import android.view.View
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseActivity
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class SettingActivity : BaseActivity(), View.OnClickListener{
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        super.initView()
        initHeadView()
        initSettingFragment()
    }

    private fun initSettingFragment() {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.setting_container, SettingFragment())
        fragmentTransaction.commit()
    }

    private fun initHeadView() {
        setting_bar.apply {
            detail_title.text = "设置"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.GONE
            detail_back.setOnClickListener(this@SettingActivity)
        }
        initColor()
    }

    private fun initColor() {
        setting_bar.setBackgroundColor(ColorUtil.getColor(this))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.detail_back -> {
                finish()
            }
        }
    }

    override fun showCreateReveal(): Boolean = false

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() {
        finish()
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
