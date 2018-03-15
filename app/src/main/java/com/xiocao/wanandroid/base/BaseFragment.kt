package com.xiocao.wanandroid.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xiocao.wanandroid.retrofit.bean.HttpResult
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.retrofit.rx.RxRetrofitCaller
import io.reactivex.Observable

/**
 * User : lijun
 * Date : 2018/3/12  16:04
 * Content : This is
 */
open class BaseFragment : RxFragment() {

    protected lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    fun <T> doNetRequest(ob: Observable<HttpResult<T>>,
                         callback: RxCallback<T>) {
        val rb = RxRetrofitCaller.Builder<T>()
        rb.setObservable(ob)
        rb.setRxRetrofitCallback(callback).subscription()
    }
}