package com.xiocao.wanandroid.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonElement
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseFragment
import com.xiocao.wanandroid.helper.UserHelper
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * User : lijun
 * Date : 2018/3/14  15:37
 * Content : This is
 */
class RegisterFragment : BaseFragment() {
    companion object {
        fun newInstance(): RegisterFragment {
            val args = Bundle()
            val fragment = RegisterFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            if (mEditAccount.text == null) {
                ToastUtils.showShortToast(mActivity, "请输入账号")
                return@setOnClickListener
            }
            if (mEditPassword.text == null || mEditConfirmPassword.text == null) {
                ToastUtils.showShortToast(mActivity, "请输入密码")
                return@setOnClickListener
            }

            if (mEditPassword.text.toString() != mEditConfirmPassword.text.toString()) {
                ToastUtils.showShortToast(mActivity, "两次密码不一致，请重新输入")
                return@setOnClickListener
            }
            doNetRequest(App.getApiService()
                    .register(mEditAccount.text.toString(), mEditPassword.text.toString(), mEditConfirmPassword.text.toString()), object : RxCallback<User> {
                override fun onSuccess(model: User) {
                    UserHelper.saveUser(model)
                    UserHelper.saveUserId(model.id.toString())
                    mActivity.finish()
                }

                override fun onFailure(code: Int, msg: String) {
                    ToastUtils.showShortToast(mActivity, msg)
                }
            })
        }
    }

}