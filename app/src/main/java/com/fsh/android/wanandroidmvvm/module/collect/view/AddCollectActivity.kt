package com.fsh.android.wanandroidmvvm.module.collect.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.fsh.android.wanandroidmvvm.common.utils.*
import com.fsh.android.wanandroidmvvm.module.collect.viewmodel.CollectViewModel
import kotlinx.android.synthetic.main.activity_add_collect.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class AddCollectActivity : BaseLifeCycleActivity<CollectViewModel>(), View.OnClickListener{
    private var mUsername: String by SPreference(Constant.USERNAME_KEY, "未登录")
    override fun initView() {
        super.initView()
        initHeaderView()
        edit_collect_author.setText(mUsername)
        edit_collect_submit.setOnClickListener(this)
        showSuccess()
    }

    private fun initHeaderView() {
        add_collect_bar.apply{
            detail_title.text = "添加收藏"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.GONE
            detail_back.setOnClickListener(this@AddCollectActivity)
        }
        initColor()
    }

    private fun initColor() {
        add_collect_bar.setBackgroundColor(ColorUtil.getColor(this))
        edit_collect_submit.setBackgroundColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mAddCollectData.observe(this, Observer {
            finish()
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_add_collect

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.detail_back -> {
                finish()
            }
            R.id.edit_collect_submit -> {
                if (edit_collect_title.text.toString()
                        .isNullOrEmpty() || edit_collect_link.text.toString().isNullOrEmpty()
                ) {
                    Toast.makeText(getApplication(), R.string.todo_empty, Toast.LENGTH_SHORT).show()
                } else {
                    mViewModel.addCollectArticle(
                        edit_collect_title.text.toString(),
                        edit_collect_author.text.toString(),
                        edit_collect_link.text.toString()
                    )
                }
            }
        }
    }


    override fun showCreateReveal(): Boolean = false

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
