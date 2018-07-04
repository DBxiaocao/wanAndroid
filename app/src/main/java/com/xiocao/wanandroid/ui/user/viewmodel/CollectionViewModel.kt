package com.xiocao.wanandroid.ui.user.viewmodel

import android.arch.lifecycle.LiveData
import com.google.gson.JsonElement
import com.xiocao.wanandroid.base.BaseViewModel
import com.xiocao.wanandroid.ui.home.TypeList

/**
 * User : lijun
 * Date : 2018/7/3  16:20
 * Content : This is
 */
class CollectionViewModel:BaseViewModel<CollectionRepository>(){
    override fun initRepository() {
        repository= CollectionRepository()
    }

    fun addCollect(id: Int):LiveData<String>{
        return repository.addCollect(id)
    }
    fun delCollect(id: Int):LiveData<String>{
        return repository.delCollect(id)
    }

    fun getCollection(page: Int): LiveData<TypeList> {
        return repository.getCollection(page)
    }
}
