package com.xiocao.wanandroid.ui.home.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseRepository
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.HttpUtil
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.TypeList

/**
 * User : lijun
 * Date : 2018/7/3  14:18
 * Content : This is
 */
class TypeRepository : BaseRepository() {
    fun getTypes(page: Int, cid: Int): LiveData<TypeList> {
        val list = MutableLiveData<TypeList>()
        HttpUtil.startRetrofitCall(App.getApiService().getTypeList(page, cid), object : RxCallback<TypeList> {
            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg, code))
            }

            override fun onSuccess(model: TypeList) {
                list.postValue(model)
            }
        })
        return list
    }
}