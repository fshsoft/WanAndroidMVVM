package com.fsh.android.wanandroidmvvm.module.search.view

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.module.common.view.ArticleListActivity
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.common.utils.KeyBoardUtil.hideKeyboard
import com.fsh.android.wanandroidmvvm.module.search.adapter.SearchHistoryAdapter
import com.fsh.android.wanandroidmvvm.module.search.model.HotKeyResponse
import com.fsh.android.wanandroidmvvm.module.search.model.bean.SearchHistory
import com.fsh.android.wanandroidmvvm.module.search.viewmodel.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.mSrlRefresh
import kotlinx.android.synthetic.main.custom_search.*
import kotlinx.android.synthetic.main.custom_search.view.*
import kotlinx.android.synthetic.main.history_foot.view.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

class SearchActivity : ArticleListActivity<SearchViewModel>() {
    private var mCurrentPageNum: Int = 0

    private var mMaxHistory = 10

    private var mHistoryIndex: Int = 0

    private var isShow = true

    private lateinit var mHistoryFootView: View

    private val mSearchHistoryAdapter by lazy { SearchHistoryAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView() {
        super.initView()
        initSearch()
        initHistory()
        initColor()
        search_back.setOnClickListener{
            finish()
        }
    }

    private fun initColor() {
        search_bar.setBackgroundColor(ColorUtil.getColor(this))
    }

    override fun onBackPressed() {
        finish()
    }

    override fun showDestroyReveal(): Boolean = true

    override fun initData() {
        mViewModel.loadSearchHistory()
        mViewModel.loadHotkey()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mHotKeyData.observe(this, Observer { response ->
            response?.let {
                showHotKeyTags(it.data)
            }
        })
        mViewModel.mSearResultData.observe(this, Observer { response ->
            response?.let {
                showSearchResultList(it.data.datas)
                if (!search_input.text.toString().isEmpty()) {
                    mViewModel.insertSearchHistory(SearchHistory(search_input.text.toString()))
                }
            }
        })
        mViewModel.mDeleteHistory.observe(this, Observer {
            mSearchHistoryAdapter.remove(mHistoryIndex)
            if (mSearchHistoryAdapter.data.isEmpty()) {
                mHistoryFootView.visibility = View.GONE
            }
        })

        mViewModel.mAddSearchHistory.observe(this, Observer {
            updateRecordPosition(search_input.text.toString())
            if (mSearchHistoryAdapter.data.isNotEmpty()) {
                mHistoryFootView.visibility = View.VISIBLE
            }
        })
        mViewModel.mSearchHistory.observe(this, Observer { history ->
            var historyNames = history?.map { it.name }?.toList()
            historyNames?.let {
                if (it.isEmpty()) {
                    mHistoryFootView.visibility = View.GONE
                } else {
                    mSearchHistoryAdapter.addData(it)
                }
            }
        })

        mViewModel.mClearHistory.observe(this, Observer {
            mSearchHistoryAdapter.setNewData(null)
            mHistoryFootView.visibility = View.GONE
        })
    }

    private fun initHistory() {
        search_history.layoutManager = LinearLayoutManager(this)
        search_history.adapter = mSearchHistoryAdapter
        search_history.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        mHistoryFootView =
            LayoutInflater.from(this).inflate(R.layout.history_foot, activity_search, false)
        mSearchHistoryAdapter.setFooterView(mHistoryFootView)
        mHistoryFootView.history_clear.setOnClickListener {
            mViewModel.deleteAllSearchHistory()
        }
        mSearchHistoryAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.history_delete) {
                mHistoryIndex = position
                mViewModel.deleteSearchHistory(SearchHistory(mSearchHistoryAdapter.data[position]))
            }
        }
        mSearchHistoryAdapter.setOnItemClickListener { _, _, position ->
            loadSearchResultByHistory(mSearchHistoryAdapter.data[position])
        }
    }

    override fun onLoadMoreData() {
       mViewModel.loadSearchResult(++mCurrentPageNum, search_input.text.toString())
    }

    override fun onRefreshData() {
        mViewModel.loadHotkey()
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
        }
    }

    private fun initSearch() {
        search_back.setOnClickListener { finish() }
        search_close.setOnClickListener {
            search_bar.search_input.setText("")
            displaySearchView()
            hideKeyboard()
        }
        search_button.setOnClickListener { view ->
            loadSearchResultByHistory(search_input.text.toString())
        }
        search_input.setOnEditorActionListener(TextView.OnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                loadSearchResultByHistory(search_input.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun loadSearchResultByHistory(key: String) {
        if (key.isEmpty()) {
            displaySearchView()
            toast("??????????????????")
            return
        }
        hideKeyboard()
        search_input.setText(key)
        search_input.setSelection(key.length)
        mViewModel.loadSearchResult(mCurrentPageNum, key)
    }

    /**
     * ???????????????????????????????????????view
     */
    private fun displaySearchView() {
        if (isShow) {
            return
        }
        search_text_top.visibility = View.VISIBLE
        search_flowlayout.visibility = View.VISIBLE
        search_text_history.visibility = View.VISIBLE
        search_history.visibility = View.VISIBLE
        mAdapter.setNewData(null)
        isShow = true
    }

    /**
     * ???????????????????????????????????????view
     */
    private fun hideSearchView() {
        if (!isShow) return
        search_text_top.visibility = View.GONE
        search_flowlayout.visibility = View.GONE
        search_text_history.visibility = View.GONE
        search_history.visibility = View.GONE
        isShow = false
    }


    private fun showHotKeyTags(hotkeyList: List<HotKeyResponse>) {
        val tags = hotkeyList.map { it.name }.toList()
        search_flowlayout.adapter = object : TagAdapter<String>(tags) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val tagText =  LayoutInflater.from(this@SearchActivity)
                    .inflate(R.layout.flow_layout, parent, false) as TextView
                tagText.setText(tags[position])
                tagText.background
                    .setColorFilter(ColorUtil.randomColor(), PorterDuff.Mode.SRC_ATOP)
                tagText.setTextColor(getColor(R.color.white))
                return tagText
            }
        }
        search_flowlayout.setOnTagClickListener { view, position, _ ->
            loadSearchResultByHistory(tags[position])
            true
        }
    }

    private fun showSearchResultList(searchResultList: List<Article>) {
        addData(searchResultList)
        hideSearchView()
    }

    private fun updateRecordPosition(name: String) {
        val records = mSearchHistoryAdapter.data
        val index = records.indexOf(name)
        if (index == -1) {
            // ??????????????????????????????????????????10????????????????????????
            if (records.size >= mMaxHistory) {
                mSearchHistoryAdapter.remove(mMaxHistory - 1)
            }
            // ???????????????????????????????????????????????????
            mSearchHistoryAdapter.addData(0, name)
            return
        }

        // ????????????????????????
        if (index != 0) {
            // ??????????????????????????????????????????????????????????????????
            mSearchHistoryAdapter.remove(index)
            mSearchHistoryAdapter.addData(0, name)
        }
    }

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        initColor()
    }
}
