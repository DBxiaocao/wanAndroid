package com.xiocao.wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * User : lijun
 * Date : 2018/7/2  10:57
 * Content : This is
 */
abstract class ArchBaseFragment<T : ViewModel> : BaseFragment() {
    protected lateinit var mViewModel: T
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getResLayout(), container, false)
        initViewModel()
        return view
    }

    abstract fun initViewModel()
    abstract fun getResLayout(): Int
}