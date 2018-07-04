package com.xiocao.wanandroid.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData

import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseRepository
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.HttpUtil
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.HomeBanner
import com.xiocao.wanandroid.ui.home.HomeList

/**
 * User : lijun
 * Date : 2018/7/2  11:09
 * Content : This is
 */
class HomeRepository : BaseRepository() {
    fun getBanner(): MutableLiveData<List<HomeBanner>> {
        val banner = MutableLiveData<List<HomeBanner>>()

        HttpUtil.startRetrofitCall(App.getApiService().getHomeBanner(), object : RxCallback<List<HomeBanner>> {
            override fun onSuccess(model: List<HomeBanner>) {
                banner.postValue(model)
            }

            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg, code))
            }
        })
        return banner
    }

    fun getList(page: Int): MutableLiveData<HomeList> {
        val list = MutableLiveData<HomeList>()
        HttpUtil.startRetrofitCall(App.getApiService().getHomeList(page), object : RxCallback<HomeList> {
            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg, code))
            }

            override fun onSuccess(model: HomeList) {
                list.postValue(model)
            }
        })
        return list
    }

}
