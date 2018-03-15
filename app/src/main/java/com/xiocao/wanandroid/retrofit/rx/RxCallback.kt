package com.xiocao.wanandroid.retrofit.rx

interface RxCallback<in T > {
    fun onSuccess(model: T)

    fun onFailure(code: Int, msg: String)
}