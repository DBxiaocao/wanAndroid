package com.xiocao.wanandroid.retrofit.exception

import android.content.Context
import android.util.MalformedJsonException

import com.google.gson.JsonParseException
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.utils.NetUtils

import org.json.JSONException

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

import retrofit2.HttpException

/**
 * description: ExceptionEngine
 * author: lijun
 * date: 17/12/27 11:15
 */

object ExceptionEngine {
    val UN_KNOWN_ERROR = 1000//未知错误
    val ANALYTIC_SERVER_DATA_ERROR = 1001//解析(服务器)数据错误
    val CONNECT_ERROR = 1003//网络连接错误
    val TIME_OUT_ERROR = 1004//网络连接超时
    val CODE_NETWORK = 1005//无网络

    fun handleException(e: Throwable): ApiException {
        e.printStackTrace()
        val ex: ApiException
        if (!NetUtils.isAvailable(App.getContext())) {
            ex = ApiException(e, CODE_NETWORK)
            ex.msg = "网络无连接,请检查重试"
        } else if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, e.code())
            ex.msg = "网络错误"  //均视为网络错误
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException || e is MalformedJsonException) {  //解析数据错误
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.msg = "解析错误"
        } else if (e is ConnectException) {//连接网络错误
            ex = ApiException(e, CONNECT_ERROR)
            ex.msg = "连接失败"
        } else if (e is SocketTimeoutException) {//网络超时
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.msg = "网络超时"
        } else if (e is ApiException) {
            ex = e
        } else {  //未知错误
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.msg = e.message ?: "未知错误"
        }
        return ex
    }
}
