package com.xiocao.wanandroid.retrofit.rx

import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.xiocao.wanandroid.retrofit.bean.HttpResult
import com.xiocao.wanandroid.retrofit.exception.ApiException

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject

/**
 * User : lijun
 * Date : 2018/3/12  16:17
 * Content : This is
 */
class RxFunction<T> : Function<HttpResult<T>, Observable<T>> {

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    override fun apply(@NonNull data: HttpResult<T>): Observable<T> {
        return when {
            data.code == 0 -> {
                val observable = Observable.just(data.data!!)
                observable.compose(RxLifecycle.bindUntilEvent<Any, ActivityEvent>(lifecycleSubject, ActivityEvent.DESTROY))
                observable
            }
            data.code == -1 -> {
                val observable = Observable.error<T>(ApiException(data.code, data.msg!!))
                observable.compose(RxLifecycle.bindUntilEvent<Any, ActivityEvent>(lifecycleSubject, ActivityEvent.DESTROY))
                observable
            }
            else -> Observable.empty()
        }
    }
}