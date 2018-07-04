package com.xiocao.wanandroid.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.ArchBaseFragment
import com.xiocao.wanandroid.helper.UserHelper
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * User : lijun
 * Date : 2018/3/14  15:37
 * Content : This is
 */
class RegisterFragment : ArchBaseFragment<LoginViewModel>() {
    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(mActivity).get(LoginViewModel::class.java)
        mViewModel.initRepository()
    }

    override fun getResLayout(): Int {
        return R.layout.fragment_register
    }

    companion object {
        fun newInstance(): RegisterFragment {
            val args = Bundle()
            val fragment = RegisterFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            mViewModel.register(mEditAccount.text.toString(), mEditPassword.text.toString(), mEditConfirmPassword.text.toString())
                    .observe(this, Observer<User> { user ->
                        if (user != null) {
                            UserHelper.saveUser(user)
                            UserHelper.saveUserId(user.id.toString())
                            mActivity.finish()
                        }
                    })
        }
        mViewModel.getError().observe(this, Observer<ErrorStatus> { error -> ToastUtils.showShortToast(mActivity, error?.message.toString()) })
    }

}