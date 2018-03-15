package com.xiocao.wanandroid.ui.category

import android.os.Bundle
import android.support.v4.app.Fragment
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.base.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_cate_info.*

class CateInfoActivity : BaseActivity() {
    companion object {
        val KEY_CATE: String = "key.cate"
    }

    private lateinit var mFragmentPagerAdapter: FragmentPagerAdapter
    private var fragments = mutableListOf<Fragment>()
    private var tabTitleList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cate_info)
        initView()
    }

    private fun initView() {
        mFragmentPagerAdapter = FragmentPagerAdapter(supportFragmentManager)
        mFragmentPagerAdapter.setFragments(fragments)
        mFragmentPagerAdapter.setTabTitleList(tabTitleList)
        mVIewPager.adapter = mFragmentPagerAdapter
        mTabLayout.setupWithViewPager(mVIewPager)
        mVIewPager.offscreenPageLimit = tabTitleList.size
    }

}
