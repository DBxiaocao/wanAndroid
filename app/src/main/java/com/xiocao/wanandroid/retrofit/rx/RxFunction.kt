package com.xiocao.wanandroid.retrofit.rx

import com.xiocao.wanandroid.retrofit.bean.HttpResult
import com.xiocao.wanandroid.retrofit.exception.ApiException

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * User : lijun
 * Date : 2018/3/12  16:17
 * Content : This is
 */
class RxFunction<T> : Function<HttpResult<T>, Observable<T>> {


    override fun apply(@NonNull data: HttpResult<T>): Observable<T> {
        return when {
            data.code == 0 -> {
                val observable = Observable.just(data.data!!)
                observable
            }
            data.code == -1 -> {
                val observable = Observable.error<T>(ApiException(data.code, data.msg!!))
                observable
            }
            else -> Observable.empty()
        }
    }
}