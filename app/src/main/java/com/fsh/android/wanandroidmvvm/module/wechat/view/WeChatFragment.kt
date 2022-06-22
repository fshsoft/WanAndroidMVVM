package com.fsh.android.wanandroidmvvm.module.wechat.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fsh.android.wanandroidmvvm.R

import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.module.wechat.adapter.WeChatTabAdapter
import com.fsh.android.wanandroidmvvm.module.wechat.model.WeChatTabNameResponse
import com.fsh.android.wanandroidmvvm.module.wechat.viewmodel.WeChatViewModel
import kotlinx.android.synthetic.main.fragment_wechat.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 */
class WeChatFragment : BaseLifeCycleFragment<WeChatViewModel>() {

    companion object {
        fun getInstance(): WeChatFragment? {
            return WeChatFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    override fun initView() {
        super.initView()
        initColor()
    }

    private fun initColor() {
        wechat_tab.dividerColor = ColorUtil.getColor(requireContext())
        wechat_tab.indicatorColor = ColorUtil.getColor(requireContext())
    }

    override fun initData() {
        super.initData()
        mViewModel.loadWeChatTabName()
    }

    override fun initDataObserver() {
        mViewModel.mWChatTabData.observe(this, Observer { response ->
            response?.let{
                initWeChatArticleFragment(it)
            }
        })
    }

    private fun initWeChatArticleFragment(dataList : List<WeChatTabNameResponse>) {
        val tabs = arrayListOf<String>()

        val fragments = arrayListOf<Fragment>()

        for (data in dataList) {
            tabs.add(data.name)
            fragments.add(WeChatArticleFragment.newInstance(data.id))
        }
        wechat_viewpager.adapter = WeChatTabAdapter(childFragmentManager, tabs, fragments)
        wechat_viewpager.offscreenPageLimit = tabs.size
        wechat_tab.setViewPager(wechat_viewpager)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}