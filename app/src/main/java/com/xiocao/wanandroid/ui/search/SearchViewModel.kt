package com.xiocao.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.text.TextUtils
import com.xiocao.wanandroid.base.BaseViewModel
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.ui.home.TypeList

/**
 * User : lijun
 * Date : 2018/7/4  14:30
 * Content : This is
 */
class SearchViewModel : BaseViewModel<SearchRepository>() {
    override fun initRepository() {
        repository = SearchRepository()
    }

    fun getHotKey(): LiveData<List<HotSearch>> {
        return repository.getHotSearch()
    }

    fun getSearchList(page: Int, key: String): LiveData<TypeList> {
        val data = MutableLiveData<TypeList>()
        if (TextUtils.isEmpty(key)) {
            repository.getErrMsg().postValue(ErrorStatus.error("关键字不能为空"))
            return data
        }
        return repository.getSearchList(page, key)
    }

}
