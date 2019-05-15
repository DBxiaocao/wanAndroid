package com.xiocao.wanandroid.base

import androidx.lifecycle.ViewModel
import android.os.Bundle

/**
 * User : lijun
 * Date : 2018/7/2  10:57
 * Content : This is
 */
abstract class ArchBaseActivity <T :ViewModel> :BaseActivity(){
    protected lateinit var mViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    abstract fun initViewModel()
}