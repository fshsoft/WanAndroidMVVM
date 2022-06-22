package com.fsh.android.wanandroidmvvm.module.project.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created with Android Studio.
 * Description:
 */
class ProjectTabAdapter(
    fragmentManager: FragmentManager,
    val tabs: List<String>,
    val fragments: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence? = tabs[position]
}