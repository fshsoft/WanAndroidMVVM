package com.fsh.android.wanandroidmvvm.module.common.view

import android.graphics.Color
import androidx.lifecycle.Observer
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.fsh.android.wanandroidmvvm.module.common.adapter.ArticleAdapter
import com.fsh.android.wanandroidmvvm.common.state.UserInfo
import com.fsh.android.wanandroidmvvm.common.state.callback.CollectListener
import com.fsh.android.wanandroidmvvm.common.state.callback.LoginSuccessListener
import com.fsh.android.wanandroidmvvm.common.state.callback.LoginSuccessState
import com.fsh.android.wanandroidmvvm.common.utils.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 */
abstract class ArticleListFragment<VM : ArticleViewModel<*>> : BaseLifeCycleFragment<VM>(),
    LoginSuccessListener, CollectListener {

    private var mCollectState: Boolean = false

    private var mCurrentItem: Int = 0

    protected lateinit var mAdapter: ArticleAdapter

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        initRefresh()
        mRvArticle?.layoutManager = SpeedLayoutManager(context, 10f)
        mAdapter =
            ArticleAdapter(
                R.layout.article_item,
                null
            )
        mRvArticle?.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)

            article?.let {
                mViewModel.addFootPrint(article)
                CommonUtil.startWebView(requireContext(), it.link, it.title)
            }
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            CommonUtil.Vibrate(requireContext(), 50)
            UserInfo.instance.collect(requireContext(), position, this)
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
        LoginSuccessState.addListener(this)
    }


    private fun initRefresh() {
        // ?????????????????????loading??????
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(requireContext()))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    /**
     * ????????????
     */
    abstract fun onRefreshData()

    /**
     * ??????????????????
     */
    abstract fun onLoadMoreData()

    fun addData(articleList: List<Article>) {
        // ????????????????????????????????????
        if (articleList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }
        // ????????????????????????????????????????????????
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(articleList)
            mAdapter.loadMoreComplete()
            return
        }

        // ?????????????????????????????????
        mAdapter.addData(articleList)
        mAdapter.loadMoreComplete()
    }

    override fun initDataObserver() {
        mViewModel.mCollectData.observe(this, Observer {
            var article = mAdapter.getItem(mCurrentItem)
            article?.let {
                it.collect = !mCollectState
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun collect(position: Int) {
        var article = mAdapter.getItem(position)
        article?.let {
            mCurrentItem = position
            mCollectState = it.collect
            if (mCollectState) mViewModel.unCollectCo(it.id) else mViewModel.collectCo(it.id)
        }
    }

    override fun loginSuccess(userName: String, userId: String, collectArticleIds: List<Int>?) {
        collectArticleIds?.let {
            it.forEach { id ->
                mAdapter.data.forEach { article ->
                    if (article.id == id) {
                        article.collect = true
                    }
                }
            }
        } ?: let {
            mAdapter.data.forEach { article ->
                article.collect = false
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListener(this)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(requireContext()))
        mAdapter.notifyDataSetChanged()
    }
}