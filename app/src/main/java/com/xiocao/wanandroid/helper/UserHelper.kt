package com.xiocao.wanandroid.helper

import com.google.gson.Gson
import com.xiocao.wanandroid.ui.login.User
import com.xiocao.wanandroid.utils.SPUtils

/**
 * User : lijun
 * Date : 2018/3/14  15:50
 * Content : This is
 */
object UserHelper {

    private val SP_KEY_USER = "sp.user.key"
    private val SP_KEY_USERID = "sp.user.key.id"

    val user: User
        get() {
            val user = Gson().fromJson(SPUtils.instance.getString(SP_KEY_USER), User::class.java)
            return user ?: User()
        }

    val userId: String
        get() = SPUtils.instance.getString(SP_KEY_USERID)

    fun saveUser(user: User) {
        SPUtils.instance.put(SP_KEY_USER, Gson().toJson(user))
    }

    fun saveUserId(userid: String) {
        SPUtils.instance.put(SP_KEY_USERID, userid)
    }
}
