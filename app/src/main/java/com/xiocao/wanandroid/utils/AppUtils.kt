package com.xiocao.wanandroid.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider

import java.io.File

/**
 * Content:
 * Created By WDY
 * Date: 2017/10/13 18:24
 */

object AppUtils {

    fun getLocalVersionCode(activity: Activity): Int {
        var localVersion = 0
        try {
            val packageInfo = activity.applicationContext.packageManager
                    .getPackageInfo(activity.packageName, 0)
            localVersion = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }

    fun getLocalVersionName(activity: Activity): String {
        var localVersion = ""
        try {
            val packageInfo = activity.applicationContext.packageManager
                    .getPackageInfo(activity.packageName, 0)
            localVersion = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }
}
