package com.fsh.android.wanandroidmvvm.module.project.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.fsh.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil
import com.fsh.android.wanandroidmvvm.module.project.adapter.ProjectTabAdapter
import com.fsh.android.wanandroidmvvm.module.project.model.ProjectTabResponse
import com.fsh.android.wanandroidmvvm.module.project.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 */
class ProjectFragment : BaseLifeCycleFragment<ProjectViewModel>() {

    companion object {
        fun getInstance(): ProjectFragment? {
            return ProjectFragment()
        }
    }
    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initView() {
        super.initView()
        initColor()
    }

    private fun initColor() {
        project_tab.dividerColor = ColorUtil.getColor(requireContext())
        project_tab.indicatorColor = ColorUtil.getColor(requireContext())
    }

    override fun initData() {
        super.initData()
        mViewModel.loadProjectTab()
    }

    override fun initDataObserver() {
        mViewModel.mProjectTabData.observe(this, Observer { response ->
            response?.let {
                initProjectArticleFragment(it)
            }
        })
    }

    private fun initProjectArticleFragment(dataList : List<ProjectTabResponse>) {
        val tabs = arrayListOf<String>()
        val fragments = arrayListOf<Fragment>()
        for (data in dataList) {
            tabs.add(data.name)
            fragments.add(ProjectArticleFragment.newInstance(data.id))
        }
        project_viewpager.adapter = ProjectTabAdapter(childFragmentManager, tabs, fragments)
        project_tab.setViewPager(project_viewpager)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}