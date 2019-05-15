package com.xiocao.wanandroid.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.xiocao.wanandroid.BuildConfig

import com.xiocao.wanandroid.retrofit.rx.RxRetrofitCaller
import com.xiocao.wanandroid.retrofit.tools.Preference
import timber.log.Timber

/**
 * User : lijun
 * Date : 2018/3/12  16:36
 * Content : This is
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        mContext = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Preference.setContext(applicationContext)
    }


    companion object {

        private val apiService = null
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        fun getApiService(): ApiService {
            return apiService ?: RxRetrofitCaller.instance.create(ApiService::class.java, "http://www.wanandroid.com/")
        }

        fun getContext(): Context {
            return mContext
        }
    }

}
