package com.xiocao.wanandroid.ui.home.viewmodel

import androidx.lifecycle.LiveData
import com.xiocao.wanandroid.base.BaseViewModel
import com.xiocao.wanandroid.ui.home.TypeList

/**
 * User : lijun
 * Date : 2018/7/3  14:24
 * Content : This is
 */
class TypeViewModel : BaseViewModel<TypeRepository>() {
    override fun initRepository() {
        repository = TypeRepository()
    }
    fun getTypes(page:Int,cid:Int):LiveData<TypeList>{
        return repository.getTypes(page,cid)
    }
}