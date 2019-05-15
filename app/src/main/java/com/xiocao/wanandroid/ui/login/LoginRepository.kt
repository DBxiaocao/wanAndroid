package com.xiocao.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseRepository
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.HttpUtil
import com.xiocao.wanandroid.retrofit.rx.RxCallback

/**
 * User : lijun
 * Date : 2018/7/3  15:17
 * Content : This is
 */
class LoginRepository : BaseRepository() {
    fun login(account: String, pwd: String): MutableLiveData<User> {
        val user = MutableLiveData<User>()
        HttpUtil.startRetrofitCall(App.getApiService().login(account, pwd), object : RxCallback<User> {
            override fun onSuccess(model: User) {
                user.postValue(model)
            }

            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg, code))
            }
        })
        return user
    }

    fun register(username: String, password: String, repassword: String): MutableLiveData<User> {
        val user = MutableLiveData<User>()
        HttpUtil.startRetrofitCall(App.getApiService().register(username, password, repassword),object :RxCallback<User>{
            override fun onSuccess(model: User) {
                user.postValue(model)
            }

            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg,code))
            }
        })
        return user
    }
}
