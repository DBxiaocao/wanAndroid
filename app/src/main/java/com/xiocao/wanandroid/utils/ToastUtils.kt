package com.xiocao.wanandroid.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

object ToastUtils {

    private var sToast: Toast? = null

    /**
     * 弹出长时间的Toast提示框.
     *
     * @param c    application
     * @param text String
     */
    fun showLongToast(c: Context, text: String): Toast {
        return show(c, text, Toast.LENGTH_LONG)
    }

    /**
     * 弹出长时间的Toast提示框.
     *
     * @param c    application
     * @param text String
     */
    fun showShortToast(c: Context, text: String): Toast {
        return show(c, text, Toast.LENGTH_SHORT)
    }

    private fun show(c: Context, text: String, duration: Int): Toast {
        if (sToast == null) {
            sToast = Toast.makeText(c, text, duration)
            val textView = sToast!!.view
                    .findViewById<View>(android.R.id.message) as TextView
            textView.textSize = 16f
        } else {
            sToast!!.setText(text)
            sToast!!.duration = duration
        }
        sToast!!.show()
        return sToast as Toast
    }
}
