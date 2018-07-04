package com.xiocao.wanandroid.retrofit

/**
 * User : lijun
 * Date : 2018/7/2  17:12
 * Content : This is
 */
class ErrorStatus {
    var message: String? = null

    var error: Boolean = false

    var code: Int = 0

    companion object {

        internal fun success(msg:String?): ErrorStatus {
            val err = ErrorStatus()
            err.error = true
            return err
        }

        internal fun error(error: String?, code: Int): ErrorStatus {
            val err = ErrorStatus()
            err.message = error
            err.error = false
            err.code = code
            return err
        }

        internal fun error(error: String): ErrorStatus {
            val err = ErrorStatus()
            err.message = error
            err.error = false
            return err
        }
    }
}
