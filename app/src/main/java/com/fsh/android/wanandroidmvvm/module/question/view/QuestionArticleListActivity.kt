package com.fsh.android.wanandroidmvvm.module.question.view

import android.view.View
import androidx.lifecycle.Observer
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.module.common.view.ArticleListActivity
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.module.question.viewModel.QuestionViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class QuestionArticleListActivity : ArticleListActivity<QuestionViewModel>() {
    private var mCurrentPage: Int = 1

    private lateinit var headerView: View

    override fun initView() {
        super.initView()
        initHeaderView()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mQuestionData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 1
        mViewModel.loadQuestionList(mCurrentPage)
    }

    override fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadQuestionList(mCurrentPage)
    }

    override fun onLoadMoreData() {
        mViewModel.loadQuestionList(++mCurrentPage)
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "每日一问"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.GONE
            detail_back.setOnClickListener { onBackPressed() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(ColorUtil.getColor(this))
    }

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() = finish()

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        initColor()
    }
}