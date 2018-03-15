package com.xiocao.wanandroid.retrofit.bean

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * description: HttpResult
 * author: lijun
 * date: 17/12/10 下午8:45
 */
class HttpResult<T> : Serializable {
    @SerializedName("data")
    var data: T? = null
    @SerializedName("errorMsg")
    var msg: String? = null
    @SerializedName("errorCode")
    var code: Int = 0

}
