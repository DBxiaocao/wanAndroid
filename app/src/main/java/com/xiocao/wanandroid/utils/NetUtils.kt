package com.xiocao.wanandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetUtils {

    /**
     * 网络是否可用
     * @param context
     * @return
     */
    fun isAvailable(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info?.isAvailable ?: false
    }

    /**
     * 是否是wifi连接
     * @param context
     * @return
     */
    fun isWifi(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return if (info != null) {
            info.type == ConnectivityManager.TYPE_WIFI
        } else false
    }

    /**
     * 是否是移动数据连接
     * @param context
     * @return
     */
    fun isMobileData(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return if (info != null) {
            info.type == ConnectivityManager.TYPE_MOBILE
        } else false
    }

    @SuppressLint("MissingPermission")
    private fun getNetworkInfo(context: Context?): NetworkInfo? {
        if (context != null) {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return manager.activeNetworkInfo
        }
        return null
    }
}
