package com.xiocao.wanandroid.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.xiocao.wanandroid.retrofit.rx.RxRetrofitCaller
import com.xiocao.wanandroid.retrofit.tools.Preference

/**
 * User : lijun
 * Date : 2018/3/12  16:36
 * Content : This is
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        })
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
