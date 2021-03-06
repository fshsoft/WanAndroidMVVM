package com.fsh.android.wanandroidmvvm.module.rank.view

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.CommonUtil
import com.fsh.android.wanandroidmvvm.common.utils.DisplayUtil
import com.fsh.android.wanandroidmvvm.module.rank.adapter.IntegralHistoryAdapter
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralHistoryResponse
import com.fsh.android.wanandroidmvvm.module.rank.viewmodel.RankViewModel
import kotlinx.android.synthetic.main.fragment_article_list.mRvArticle
import kotlinx.android.synthetic.main.fragment_article_list.mSrlRefresh
import kotlinx.android.synthetic.main.integral_header_view.view.*
import org.greenrobot.eventbus.Subscribe

class IntegralHistoryActivity : BaseLifeCycleActivity<RankViewModel>() {
    private var mCurrentPage: Int = 1
    private lateinit var headerView: View
    private lateinit var mAdapter: IntegralHistoryAdapter
    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        mAdapter = IntegralHistoryAdapter(R.layout.integral_history_item, null)
        initHeaderView()
        initRefresh()
        mRvArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRvArticle.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 1
        mViewModel.loadIntegralHistoryList(mCurrentPage)
        mViewModel.loadMeRankInfo()
    }

    override fun initDataObserver() {
        mViewModel.mIntegralHistoryListData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
        mViewModel.mMeRankInfo.observe(this, Observer { response ->
            response.let {
                DisplayUtil.startIntegralTextAnim(headerView.integral_text_anim, it.data.coinCount, "")
                DisplayUtil.startIntegralTextAnim(headerView.integral_level_anim, it.data.level, "??????:")
                DisplayUtil.startIntegralTextAnim(headerView.integral_rank_anim, it.data.rank, "??????:")
            }
        })
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadIntegralHistoryList(mCurrentPage)
    }

    fun onLoadMoreData() {
        mViewModel.loadIntegralHistoryList(++mCurrentPage)
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.integral_header_view, null)
        headerView.apply {
            integral_title.text = "????????????"
            integral_rule.setOnClickListener { onRulePressed() }
            integral_back.setOnClickListener { onBackPressed() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.integral_bar.setBackgroundColor(ColorUtil.getColor(this))
    }

    private fun initRefresh() {
        // ?????????????????????loading??????
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    fun addData(integralHistoryList: List<IntegralHistoryResponse>) {

        // ????????????????????????????????????
        if (integralHistoryList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // ????????????????????????????????????????????????
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(integralHistoryList)
            mAdapter.loadMoreComplete()
            return
        }

        // ?????????????????????????????????
        mAdapter.addData(integralHistoryList)
        mAdapter.loadMoreComplete()
    }

    override fun showCreateReveal(): Boolean = false
    override fun showDestroyReveal(): Boolean = false
    override fun onBackPressed() = finish()

    private fun onRulePressed() {
        CommonUtil.startWebView(
            this,
            "https://www.wanandroid.com/blog/show/2653",
            getString(R.string.rank_rule)
        )
    }


    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
    }
}
