package com.xiocao.wanandroid.retrofit.rx


import com.xiocao.wanandroid.retrofit.RetrofitUtil
import com.xiocao.wanandroid.retrofit.bean.HttpResult

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * User: Luozi
 * Date: 2016-07-11
 * Content:
 */

class RxRetrofitCaller {

    fun <T> create(service: Class<T>): T {
        return RetrofitUtil.instance.retrofit().create(service)
    }

    fun <T> create(service: Class<T>, url: String): T {
        return RetrofitUtil.instance.retrofit(url).create(service)
    }

    private fun <T> subscription(params: NetworkParams<T>) {

        params.observable!!
                .flatMap(RxFunction())
                .compose(RxSchedulers.composeObs())
                .subscribe(params.subscriber!!)
    }

    private class NetworkParams<T> {
        var observable: Observable<HttpResult<T>>? = null
        internal var subscriber: RxObserver<T>? = null
    }

    class Builder<T> {

        private val mNetworkParams: NetworkParams<T> = NetworkParams()

        fun setObservable(observable: Observable<HttpResult<T>>): Builder<*> {
            mNetworkParams.observable = observable
            return this
        }

        fun setRxRetrofitCallback(callback: RxCallback<T>): Builder<*> {
            mNetworkParams.subscriber = object : RxObserver<T>(callback) {

            }
            return this
        }

        /**
         * 创建网络请求.
         */
        private fun create(): RxRetrofitCaller {
            return instance
        }

        /**
         * 启动网络请求.
         */
        fun subscription(): Builder<*> {
            val caller = create()
            caller.subscription(mNetworkParams)
            return this
        }

    }

    companion object {

        private var mCaller: RxRetrofitCaller? = null
        val instance: RxRetrofitCaller
            get() {
                if (null == mCaller) {
                    synchronized(RxRetrofitCaller::class.java) {
                        mCaller = RxRetrofitCaller()
                    }
                }
                return this.mCaller!!
            }
    }
}
