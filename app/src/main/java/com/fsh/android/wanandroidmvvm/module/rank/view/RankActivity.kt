package com.fsh.android.wanandroidmvvm.module.rank.view

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.startActivity
import com.fsh.android.wanandroidmvvm.module.rank.adapter.RankAdapter
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralResponse
import com.fsh.android.wanandroidmvvm.module.rank.viewmodel.RankViewModel
import kotlinx.android.synthetic.main.activity_rank.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_article_list.mRvArticle
import kotlinx.android.synthetic.main.fragment_article_list.mSrlRefresh
import org.greenrobot.eventbus.Subscribe

class RankActivity : BaseLifeCycleActivity<RankViewModel>() {
    private var mCurrentPage: Int = 1
    private lateinit var headerView: View
    private lateinit var mAdapter: RankAdapter
    override fun getLayoutId(): Int = R.layout.activity_rank

    override fun initView() {
        super.initView()
        mAdapter = RankAdapter(R.layout.rank_item, null)
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
        mViewModel.loadRankList(mCurrentPage)
        mViewModel.loadMeRankInfo()
    }

    override fun initDataObserver() {
        mViewModel.mRankListData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
        mViewModel.mMeRankInfo.observe(this, Observer { response ->
            response.let {
                integral_melevel.text = "?????????" + it.data.level.toString()
                integral_mename.text = "?????????" + it.data.username
                integral_mecount.text = "?????????" + it.data.coinCount.toString()
            }
        })
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadRankList(mCurrentPage)
        mViewModel.loadMeRankInfo()
    }

    fun onLoadMoreData() {
        mViewModel.loadRankList(++mCurrentPage)
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "????????????"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.VISIBLE
            detail_search.setImageResource(R.drawable.ic_history)
            detail_search.setOnClickListener { onHistoryPressed() }
            detail_back.setOnClickListener { onBackPressed() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(ColorUtil.getColor(this))
        integral_mecard.setCardBackgroundColor(ColorUtil.getColor(this))
    }

    private fun initRefresh() {
        // ?????????????????????loading??????
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    fun addData(integralList: List<IntegralResponse>) {

        // ????????????????????????????????????
        if (integralList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // ????????????????????????????????????????????????
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(integralList)
            mAdapter.loadMoreComplete()
            return
        }

        // ?????????????????????????????????
        mAdapter.addData(integralList)
        mAdapter.loadMoreComplete()
    }

    override fun showDestroyReveal(): Boolean = true
    override fun onBackPressed() = finish()

    private fun onHistoryPressed() {
        startActivity<IntegralHistoryActivity>(this)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
    }

}
