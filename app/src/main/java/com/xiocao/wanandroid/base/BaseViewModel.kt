package com.xiocao.wanandroid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xiocao.wanandroid.retrofit.ErrorStatus

/**
 * User : lijun
 * Date : 2018/7/3  11:51
 * Content : This is
 */
 abstract class BaseViewModel<R :BaseRepository> :ViewModel() {
    protected lateinit var repository: R
    fun getError(): LiveData<ErrorStatus>{
        return repository.getErrMsg()
    }
    abstract fun initRepository()
}
