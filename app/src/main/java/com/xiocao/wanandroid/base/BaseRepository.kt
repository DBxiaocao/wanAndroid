package com.xiocao.wanandroid.base

import android.arch.lifecycle.MutableLiveData
import com.xiocao.wanandroid.retrofit.ErrorStatus

/**
 * User : lijun
 * Date : 2018/7/3  11:55
 * Content : This is
 */
open class BaseRepository {
    protected var error = MutableLiveData<ErrorStatus>()

    fun getErrMsg(): MutableLiveData<ErrorStatus> {
        return error
    }

}
