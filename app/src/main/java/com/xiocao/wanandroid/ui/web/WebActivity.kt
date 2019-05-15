package com.xiocao.wanandroid.ui.web

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.gson.JsonElement
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.utils.ToastUtils
import android.content.Intent
import com.xiocao.wanandroid.base.ArchBaseActivity
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.ui.user.viewmodel.CollectionViewModel


class WebActivity : ArchBaseActivity<CollectionViewModel>() {
    override fun initViewModel() {
        mViewModel=ViewModelProviders.of(mActivity).get(CollectionViewModel::class.java)
        mViewModel.initRepository()
    }

    var isCollect: Boolean = false

    companion object {
        val KEY_WEB_URL: String = "key.web.url"
        val KEY_WEB_ID: String = "key.web.id"
        val KEY_WEB_COLLECT: String = "key.web.collect"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setEnabledNavigation(true)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mFrameContent, WebFragment.newInstance(intent.getStringExtra(KEY_WEB_URL)))
                .commit()
    }

    override fun onStart() {
        super.onStart()
        isCollect = intent.getBooleanExtra(KEY_WEB_COLLECT, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra(KEY_WEB_URL))
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "分享至"))
            }
            R.id.navigation_collection -> {
                if (isCollect)
                    delCollect()
                else
                    addCollect()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.web_options_menu, menu)
        if (isCollect)
            menu.getItem(1).run {
                title = "取消收藏"
            }
        else
            menu.getItem(1).run {
                title = "收藏"
            }
        return super.onCreateOptionsMenu(menu)
    }


    private fun addCollect() {
        mViewModel.addCollect(intent.getIntExtra(KEY_WEB_ID, 0)).observe(this, Observer<String>{ status-> ToastUtils.showShortToast(mActivity,status.toString()) })
    }

    private fun delCollect() {
        mViewModel.delCollect(intent.getIntExtra(KEY_WEB_ID, 0)).observe(this, Observer<String>{ status-> ToastUtils.showShortToast(mActivity,status.toString()) })
    }

}
