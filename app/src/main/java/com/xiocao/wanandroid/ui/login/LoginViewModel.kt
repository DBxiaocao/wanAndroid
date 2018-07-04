package com.xiocao.wanandroid.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.xiocao.wanandroid.base.BaseViewModel
import com.xiocao.wanandroid.retrofit.ErrorStatus

/**
 * User : lijun
 * Date : 2018/7/3  15:16
 * Content : This is
 */
class LoginViewModel : BaseViewModel<LoginRepository>() {
    override fun initRepository() {
        repository = LoginRepository()
    }

    fun login(account: String, pwd: String): LiveData<User> {
        var user = MutableLiveData<User>()
        if (TextUtils.isEmpty(account)) {
            repository.getErrMsg().postValue(ErrorStatus.error("请输入账号"))
            return user
        }
        if (TextUtils.isEmpty(pwd)) {
            repository.getErrMsg().postValue(ErrorStatus.error("请输入密码"))
            return user
        }
        user = repository.login(account, pwd)
        return user
    }

    fun register(username: String, password: String, repassword: String): LiveData<User> {
        var user = MutableLiveData<User>()
        if (TextUtils.isEmpty(username)) {
            repository.getErrMsg().postValue(ErrorStatus.error("请输入账号"))
            return user
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            repository.getErrMsg().postValue(ErrorStatus.error("请输入密码"))
            return user
        }

        if (TextUtils.isEmpty(repassword)) {
            repository.getErrMsg().postValue(ErrorStatus.error("两次密码不一致，请重新输入"))
            return user
        }
        user = repository.register(username, password, repassword)
        return user
    }

}