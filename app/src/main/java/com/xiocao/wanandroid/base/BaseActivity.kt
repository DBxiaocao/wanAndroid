package com.xiocao.wanandroid.base

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.xiocao.wanandroid.R
import kotlinx.android.synthetic.main.base_activity.*
import com.xiocao.wanandroid.retrofit.rx.RxRetrofitCaller
import com.xiocao.wanandroid.retrofit.bean.HttpResult
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.utils.StatusBarUtil
import io.reactivex.Observable


/**
 * User : lijun
 * Date : 2018/3/12  15:24
 * Content : This is
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.base_activity)
        mActivity = this
        mToolBar.title = ""
        setSupportActionBar(mToolBar)
        StatusBarUtil.darkMode(mActivity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setPaddingSmart(mActivity, mToolBar)
        }
    }

    override fun setContentView(layoutId: Int) {
        val view = LayoutInflater.from(mActivity).inflate(layoutId, mRootLayout, false)
        setContentView(view)
    }

    override fun setContentView(view: View) {
        mRootLayout.addView(view)
    }

    fun hideToolBar() {
        if (mToolBar.visibility != View.GONE) {
            mToolBar.visibility = View.GONE
        }
    }

    fun showToolBar() {
        if (mToolBar.visibility != View.VISIBLE) {
            mToolBar.visibility = View.VISIBLE
        }
    }

    fun setCenterTitle(title: CharSequence) {
        mToolBar.title = title
    }

    fun setNavigationIcon(resId: Int) {
        mToolBar.setNavigationIcon(resId)
    }

    fun setNavigationIcon(icon: Drawable) {
        mToolBar.navigationIcon = icon
    }

    fun setEnabledNavigation(isEnable: Boolean) {
        val bar = supportActionBar ?: return
        bar.setDisplayHomeAsUpEnabled(isEnable)
        if (isEnable) {
            mToolBar.setNavigationOnClickListener { finish() }
        }
    }

    fun getToolBar(): Toolbar {
        return mToolBar
    }

    @SuppressLint("CheckResult")
    fun <T> doNetRequest(ob: Observable<HttpResult<T>>,
                         callback: RxCallback<T>) {
        ob.`as`( AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
        val rb = RxRetrofitCaller.Builder<T>()
        rb.setObservable(ob)
        rb.setRxRetrofitCallback(callback).subscription()
    }

}