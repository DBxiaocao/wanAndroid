package com.xiocao.wanandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.ui.category.CateFragment
import com.xiocao.wanandroid.ui.home.HomeFragment
import com.xiocao.wanandroid.ui.user.UserFragment
import com.xiocao.wanandroid.helper.FragmentHelper
import com.xiocao.wanandroid.ui.search.SearchActivity
import com.xiocao.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * User : lijun
 * Date : 2018/3/12
 * Content : This is
 */
class MainActivity : BaseActivity() {
    private lateinit var mFragmentHelper: FragmentHelper
    private var isOptionsMenu: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCenterTitle("首页")
        initView()
    }

    private fun initView() {
        mFragmentHelper = FragmentHelper(this, supportFragmentManager, R.id.frame_content)
        mFragmentHelper.addFragmentItem(FragmentHelper.FragmentInfo(HomeFragment.newInstance()))
        mFragmentHelper.addFragmentItem(FragmentHelper.FragmentInfo(CateFragment.newInstance()))
        mFragmentHelper.addFragmentItem(FragmentHelper.FragmentInfo(UserFragment.newInstance()))
        mFragmentHelper.show(HomeFragment::class.java.simpleName)
        navigation.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.navigation_home -> {
                    isOptionsMenu = true
                    setCenterTitle("首页")
                    showToolBar()
                    mFragmentHelper.show(HomeFragment::class.java.simpleName)
                    invalidateOptionsMenu()
                    true
                }
                item.itemId == R.id.navigation_category -> {
                    isOptionsMenu = true
                    setCenterTitle("知识体系")
                    showToolBar()
                    mFragmentHelper.show(CateFragment::class.java.simpleName)
                    invalidateOptionsMenu ()
                    true
                }
                item.itemId == R.id.navigation_personal -> {
                    isOptionsMenu = false
                    setCenterTitle("我的")
                    hideToolBar()
                    mFragmentHelper.show(UserFragment::class.java.simpleName)
                    invalidateOptionsMenu ()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isOptionsMenu)
            menuInflater.inflate(R.menu.main_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_search -> {
                startActivity(Intent(mActivity, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
