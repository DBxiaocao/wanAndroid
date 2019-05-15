package com.xiocao.wanandroid.ui.home.viewmodel

import androidx.lifecycle.LiveData
import com.xiocao.wanandroid.base.BaseViewModel
import com.xiocao.wanandroid.ui.home.HomeBanner
import com.xiocao.wanandroid.ui.home.HomeList

/**
 * User : lijun
 * Date : 2018/7/2  10:59
 * Content : This is
 */
class HomeViewModel : BaseViewModel<HomeRepository>() {
    override fun initRepository() {
        repository=HomeRepository()
    }

    fun getBanner(): LiveData<List<HomeBanner>> {
        return repository.getBanner()
    }

    fun getList(page: Int): LiveData<HomeList> {
        return repository.getList(page)
    }
}
