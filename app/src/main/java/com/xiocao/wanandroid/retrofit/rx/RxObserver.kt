package com.xiocao.wanandroid.retrofit.rx


import com.xiocao.wanandroid.retrofit.exception.ExceptionEngine


import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

/**
 * description: RxObserver
 * author: lijun
 * date: 17/12/8 21:36
 */

abstract class RxObserver<T>(private val mCallback: RxCallback<T>?) : Observer<T> {
    private var mDisposable: Disposable? = null

    override fun onSubscribe(@NonNull d: Disposable) {
        mDisposable = d
    }

    override fun onError(e: Throwable) {
        if (mCallback != null) {
            val apiException = ExceptionEngine.handleException(e)
            mCallback.onFailure(apiException.code, apiException.msg!!)
        }
        mDisposable!!.dispose()
    }

    override fun onComplete() {
        mDisposable!!.dispose()
    }

    override fun onNext(data: T) {
        mCallback?.onSuccess(data)
    }

}

