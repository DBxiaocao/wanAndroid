package com.xiocao.wanandroid.retrofit

import android.app.Activity
import com.xiocao.wanandroid.retrofit.bean.HttpResult
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.retrofit.rx.RxRetrofitCaller
import io.reactivex.Observable

class HttpUtil{
    companion object {

        fun <T> startRetrofitCall(ob: Observable<HttpResult<T>>,
                                  callback: RxCallback<T>) {
            val rb = RxRetrofitCaller.Builder<T>()
            rb.setObservable(ob)
            rb.setRxRetrofitCallback(callback).subscription()
        }
    }

}
