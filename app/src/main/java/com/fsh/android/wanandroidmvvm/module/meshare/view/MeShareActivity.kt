package com.fsh.android.wanandroidmvvm.module.meshare.view

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.CommonUtil
import com.fsh.android.wanandroidmvvm.module.meshare.adapter.MeShareAdapter
import com.fsh.android.wanandroidmvvm.module.meshare.viewmodel.MeShareViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.greenrobot.eventbus.Subscribe

class MeShareActivity : BaseLifeCycleActivity<MeShareViewModel>() {
    private var mCurrentPage: Int = 1

    private lateinit var mAdapter: MeShareAdapter

    private lateinit var headerView: View

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        mAdapter = MeShareAdapter(R.layout.article_item, null)
        initHeaderView()
        initRefresh()
        mRvArticle?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRvArticle.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)
            article?.let {
                mViewModel.addFootPrint(article)
                CommonUtil.startWebView(this, it.link, it.title)
            }
        }

        mAdapter.setOnItemChildClickListener { _, _, position ->
            mViewModel.deleteMeShareArticle(mAdapter.getItem(position)!!.id)
            mAdapter.remove(position)
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 1
        mViewModel.loadMeShareArticle(mCurrentPage)
    }

    override fun initDataObserver() {
        mViewModel.mMeShareData.observe(this, Observer { response ->
            response.let {
                addData(it.data.shareArticles.datas)
            }
        })
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadMeShareArticle(mCurrentPage)
    }

    fun onLoadMoreData() {
        mViewModel.loadMeShareArticle(++mCurrentPage)
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "我的分享"
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

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    fun addData(articleList: List<Article>) {

        // 返回列表为空显示加载完毕
        if (articleList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(articleList)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(articleList)
        mAdapter.loadMoreComplete()
    }

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() = finish()

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
    }
}
