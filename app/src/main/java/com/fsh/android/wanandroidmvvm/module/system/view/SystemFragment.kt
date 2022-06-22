package com.fsh.android.wanandroidmvvm.module.system.view

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.module.system.adapter.SystemAdapter
import com.fsh.android.wanandroidmvvm.module.system.model.SystemLabelResponse
import com.fsh.android.wanandroidmvvm.module.system.model.SystemTabNameResponse
import com.fsh.android.wanandroidmvvm.module.system.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 */
class SystemFragment : BaseLifeCycleFragment<SystemViewModel>() {
    protected lateinit var mAdapter: SystemAdapter

    companion object {
        fun getInstance(): SystemFragment? {
            return SystemFragment()
        }
    }

    override fun initDataObserver() {
        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
            response?.let {
                setSystemTabData(it)
            }
        })
    }

    override fun initData() {
        mViewModel.loadSystemTab()
    }

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        initRefresh()
        mRvArticle?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = SystemAdapter(R.layout.system_item, null)
        mRvArticle.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            item?.let {
            }
        }
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(requireContext()))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    private fun onRefreshData() {
        mViewModel.loadSystemTab()
    }

    private fun setSystemTabData(systemListName : List<SystemTabNameResponse>) {
        val chileItems = arrayListOf<SystemLabelResponse>()
        // 返回列表为空显示加载完毕
        if (systemListName.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(systemListName)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(systemListName)
        mAdapter.loadMoreComplete()
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(requireContext()))
        mAdapter.notifyDataSetChanged()
    }
}