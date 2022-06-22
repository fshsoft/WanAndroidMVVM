package com.fsh.android.wanandroidmvvm.module.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.fsh.android.wanandroidmvvm.R
import com.fsh.android.wanandroidmvvm.base.view.BaseActivity
import com.fsh.android.wanandroidmvvm.common.permission.PermissionResult
import com.fsh.android.wanandroidmvvm.common.permission.Permissions
import com.fsh.android.wanandroidmvvm.common.state.UserInfo
import com.fsh.android.wanandroidmvvm.common.state.callback.LoginSuccessListener
import com.fsh.android.wanandroidmvvm.common.state.callback.LoginSuccessState
import com.fsh.android.wanandroidmvvm.common.utils.*
import com.fsh.android.wanandroidmvvm.module.footprint.view.FootPrintActivity
import com.fsh.android.wanandroidmvvm.module.home.view.HomeFragment
import com.fsh.android.wanandroidmvvm.module.navigation.view.NavigationFragment
import com.fsh.android.wanandroidmvvm.module.project.view.ProjectFragment
import com.fsh.android.wanandroidmvvm.module.question.view.QuestionArticleListActivity
import com.fsh.android.wanandroidmvvm.module.search.view.SearchActivity
import com.fsh.android.wanandroidmvvm.module.setting.SettingActivity
import com.fsh.android.wanandroidmvvm.module.square.view.SquareActivity
import com.fsh.android.wanandroidmvvm.module.system.view.SystemFragment
import com.fsh.android.wanandroidmvvm.module.wechat.view.WeChatFragment
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.layout_drawer_header.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.Subscribe
import pub.devrel.easypermissions.AppSettingsDialog


class MainActivity : BaseActivity(), LoginSuccessListener {
    // 委托属性   将实现委托给了 -> Preference
    private var mUsername: String by SPreference(Constant.USERNAME_KEY, "未登录")
    private var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
    private var mUserId: String by SPreference(Constant.USERID_KEY, "--")
    private lateinit var headView: View
    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    private val mPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private lateinit var mToolbarTitles: List<String>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initToolbarTitles()
        initToolBar()
        initDrawerLayout()
        initFabButton()
        initColor()
        initBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            switchFragment(Constant.HOME)
            checkUpdate(this, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index")
        switchFragment(mLastIndex)
    }

    private fun initToolbarTitles() {
        mToolbarTitles = arrayListOf(
            getString(R.string.navigation_home),
            getString(R.string.navigation_system),
            getString(R.string.navigation_wechat),
            getString(R.string.navigation_navigation),
            getString(R.string.navigation_project)
        )
    }

    private fun initToolBar() {
        //设置导航图标、按钮有旋转特效
        val toggle = ActionBarDrawerToggle(
            this, drawer_main, toolbar, R.string.app_name, R.string.app_name
        )
        drawer_main.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initColor() {
        toolbar.setBackgroundColor(ColorUtil.getColor(this))
        headView.setBackgroundColor(ColorUtil.getColor(this))
        bottom_navigation.setItemIconTintList(ColorUtil.getColorStateList(this))
        bottom_navigation.setItemTextColor(ColorUtil.getColorStateList(this))
        bottom_navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.white_bg))
        fab_add.setBackgroundTintList(ColorUtil.getOneColorStateList(this))
    }

    private fun initDrawerLayout() {

        // 设置 登录成功 监听
        LoginSuccessState.addListener(this)

        // 直接获取报错   error -> mNavMain.mTvName
        headView = navigation_draw.getHeaderView(0)
        headView.me_name.text = mUsername
        headView.me_image.setCircleName(mUsername)
        headView.me_info.text = "账户id: " + mUserId

        // 点击 登录
        headView.me_image.setOnClickListener { UserInfo.instance.login(this) }

        navigation_draw.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_menu_rank -> {
                    UserInfo.instance.startRankActivity(this)
                }
                R.id.nav_menu_square -> {
                    startActivity<SquareActivity>(this)
                }
                R.id.nav_menu_collect -> {
                    UserInfo.instance.startCollectActivity(this)
                }
                R.id.nav_menu_share -> {
                    UserInfo.instance.startShareActivity(this)
                }
                R.id.nav_menu_question -> {
                    startActivity<QuestionArticleListActivity>(this)
                }
                R.id.nav_menu_todo -> {
                    UserInfo.instance.startTodoActivity(this)
                }
                R.id.nav_menu_theme -> {
                    if (!isNightMode) {
                        MaterialDialog(this).show {
                            title(R.string.theme_color)
                            cornerRadius(16.0f)
                            colorChooser(
                                ColorUtil.ACCENT_COLORS,
                                initialSelection = ColorUtil.getColor(this@MainActivity),
                                subColors = ColorUtil.PRIMARY_COLORS_SUB
                            ) { dialog, color ->
                                ColorUtil.setColor(color)
                                ChangeThemeEvent().post()
                            }
                            positiveButton(R.string.done)
                            negativeButton(R.string.cancel)
                        }
                    } else {
                        Toast.makeText(this, "夜间模式无法更换主题嗷", Toast.LENGTH_SHORT).show()
                    }
                    false
                }
                R.id.nav_menu_footprint -> {
                    startActivity<FootPrintActivity>(this)
                }
                R.id.nav_menu_setting -> {
                    startActivity<SettingActivity>(this)
                }
                R.id.nav_menu_logout -> {
                    UserInfo.instance.logoutSuccess()
                }
            }

            // 关闭侧边栏
            drawer_main.closeDrawers()
            true
        }
    }

    private fun initFabButton() {
        fab_add.setOnClickListener {
            mCurrentFragment!!.mRvArticle!!.smoothScrollToPosition(0)
            val objectAnimatorX =
                ObjectAnimator.ofFloat(fab_add, "scaleX", 1.0f, 1.2f, 0.0f)
            objectAnimatorX.interpolator = AccelerateDecelerateInterpolator()
            val objectAnimatorY =
                ObjectAnimator.ofFloat(fab_add, "scaleY", 1.0f, 1.2f, 0.0f)
            objectAnimatorY.interpolator = AccelerateDecelerateInterpolator()
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(objectAnimatorX, objectAnimatorY)
            animatorSet.duration = 1000
            animatorSet.start()
        }
    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    fab_add.visibility = View.VISIBLE
                    switchFragment(Constant.HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_system -> {
                    fab_add.visibility = View.GONE
                    switchFragment(Constant.SYSTEM)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wechat -> {
                    fab_add.visibility = View.GONE
                    switchFragment(Constant.WECHAT)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_navigation -> {
                    fab_add.visibility = View.GONE
                    switchFragment(Constant.NAVIGATION)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_project -> {
                    fab_add.visibility = View.GONE
                    switchFragment(Constant.PROJECT)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction =
            fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
        setToolBarTitle(toolbar, mToolbarTitles[mLastIndex])
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                Constant.HOME -> fragment = HomeFragment.getInstance()
                Constant.SYSTEM -> fragment = SystemFragment.getInstance()
                Constant.NAVIGATION -> fragment = NavigationFragment.getInstance()
                Constant.WECHAT -> fragment = WeChatFragment.getInstance()
                Constant.PROJECT -> fragment = ProjectFragment.getInstance()
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
    }

    fun setToolBarTitle(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            //将滑动菜单显示出来
            android.R.id.home -> {
                drawer_main.openDrawer(Gravity.START)
                return true
            }
            R.id.action_scan -> {
                initCameraPermission()
            }
            R.id.action_search -> {
                startActivity<SearchActivity>(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 登录成功 回调
    override fun loginSuccess(username: String, userId: String, collectIds: List<Int>?) {
        // 进行 SharedPreference 存储
        mUsername = username
        headView.me_name.text = username
        headView.me_image.setCircleName(mUsername)
        mUserId = userId
        headView.me_info.text = "账户id: " + mUserId
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListener(this)
    }

    override fun showCreateReveal(): Boolean = false

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }

    // 获取扫描二维码的返回结果，使用浏览器打开（使用ArticleDetailActivity扫描复杂二维码会crash）
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var intent = Intent()
                intent.action = "android.intent.action.VIEW"
                intent.data = Uri.parse(data.getStringExtra(Constant.CODED_CONTENT).toString())
                startActivity(intent)
            }
        }
    }

    private fun initCameraPermission() {
        Permissions(this).request(*mPermissions).observe(
            this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {
                        val intent = Intent(this@MainActivity, CaptureActivity::class.java)
                        var config = ZxingConfig()
                        config.isShowAlbum = false
                        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config)
                        startActivityForResult(intent, Constant.REQUEST_CODE_SCAN)
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                }
            }
        )
    }
}
