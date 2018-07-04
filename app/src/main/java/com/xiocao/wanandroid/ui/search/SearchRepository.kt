package com.xiocao.wanandroid.ui.search

import android.arch.lifecycle.MutableLiveData
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseRepository
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.HttpUtil
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.HomeList
import com.xiocao.wanandroid.ui.home.TypeList

/**
 * User : lijun
 * Date : 2018/7/4  14:24
 * Content : This is
 */
class SearchRepository :BaseRepository(){
    fun getHotSearch():MutableLiveData<List<HotSearch>>{
        val hotKey=MutableLiveData<List<HotSearch>>()
        HttpUtil.startRetrofitCall(App.getApiService().getHotSearch(),object :RxCallback<List<HotSearch>>{
            override fun onSuccess(model: List<HotSearch>) {
                hotKey.postValue(model)
            }

            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg,code))
            }
        })
        return hotKey
    }

    fun getSearchList(page: Int, key: String):MutableLiveData<TypeList>{
        val list=MutableLiveData<TypeList>()
        HttpUtil.startRetrofitCall(App.getApiService().getSearch(page,key),object :RxCallback<TypeList>{
            override fun onSuccess(model: TypeList) {
                list.postValue(model)
            }

            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg,code))
            }
        })
        return list
    }
}