package com.fsh.android.wanandroidmvvm.module.account.view

import android.view.View
import androidx.lifecycle.Observer
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.fsh.android.wanandroidmvvm.common.state.UserInfo
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.startActivity
import com.fsh.android.wanandroidmvvm.module.account.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe

class LoginActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        button_login.setOnClickListener(this)
        register_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        initColor()
        showSuccess()
    }

    private fun initColor() {
        login_background.setBackgroundColor(ColorUtil.getColor(this))
        button_login.setTextColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mLoginData.observe(this, Observer {
            it?.let { loginResponse ->
                UserInfo.instance.loginSuccess(
                    loginResponse.username,
                    loginResponse.id.toString(),
                    loginResponse.collectIds
                )
                finish()
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login -> {
                mViewModel.loginCo(account_text.text.toString(), password_text.text.toString())
            }
            R.id.register_text -> {
                startActivity<RegisterActivity>(this)
                finish()
            }
            R.id.ivBack -> {
                onBackPressed()
            }
        }
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
