package com.xiocao.wanandroid.ui.login

import android.os.Bundle
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseActivity
import android.view.View
import com.xiocao.wanandroid.helper.FragmentHelper
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_view -> {
                register_view.setTextColor(resources.getColor(android.R.color.black))
                login_view.setTextColor(resources.getColor(R.color.colorPrimary))
                mFragmentHelper.show(LoginFragment::class.java.simpleName)
            }
            R.id.register_view -> {
                login_view.setTextColor(resources.getColor(android.R.color.black))
                register_view.setTextColor(resources.getColor(R.color.colorPrimary))
                mFragmentHelper.show(RegisterFragment::class.java.simpleName)
            }
            R.id.mIvBack -> {
                onBackPressed()
            }
        }
    }

    private lateinit var mFragmentHelper: FragmentHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        hideToolBar()
        mFragmentHelper = FragmentHelper(mActivity, supportFragmentManager, R.id.gate_content_frame)
        mFragmentHelper.addFragmentItem(FragmentHelper.FragmentInfo(LoginFragment.newInstance()))
        mFragmentHelper.addFragmentItem(FragmentHelper.FragmentInfo(RegisterFragment.newInstance()))
        mFragmentHelper.show(LoginFragment::class.java.simpleName)
        login_view.setOnClickListener(this)
        register_view.setOnClickListener(this)
        mIvBack.setOnClickListener(this)
    }

    private fun onCheckText(id: Int) {
        login_view.setTextColor(resources.getColor(android.R.color.black))
        register_view.setTextColor(resources.getColor(R.color.colorPrimary))
    }

}
