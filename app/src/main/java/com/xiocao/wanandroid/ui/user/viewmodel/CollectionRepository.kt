package com.xiocao.wanandroid.ui.user.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseRepository
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.HttpUtil
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.TypeList

/**
 * User : lijun
 * Date : 2018/7/3  16:15
 * Content : This is
 */
class CollectionRepository:BaseRepository(){
    fun addCollect(id: Int):MutableLiveData<String>{
        val status=MutableLiveData<String>()
        HttpUtil.startRetrofitCall(App.getApiService().addCollect(id),object :RxCallback<JsonElement>{
            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg,code))
            }

            override fun onSuccess(model: JsonElement) {
                status.postValue("收藏成功")
            }
        })
        return status
    }
    fun delCollect(id: Int):MutableLiveData<String>{
        val status=MutableLiveData<String>()
        HttpUtil.startRetrofitCall(App.getApiService().delCollect(id),object :RxCallback<JsonElement>{
            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg,code))
            }

            override fun onSuccess(model: JsonElement) {
                status.postValue("取消收藏成功")
            }
        })
        return status
    }

    fun getCollection(page: Int): LiveData<TypeList> {
        val list = MutableLiveData<TypeList>()
        HttpUtil.startRetrofitCall(App.getApiService().getCollection(page), object : RxCallback<TypeList> {
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
