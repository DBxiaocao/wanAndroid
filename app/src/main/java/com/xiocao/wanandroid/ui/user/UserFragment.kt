package com.xiocao.wanandroid.ui.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseFragment
import com.xiocao.wanandroid.helper.UserHelper
import com.xiocao.wanandroid.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * User : lijun
 * Date : 2018/3/13  11:15
 * Content : This is
 */
class UserFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.mTvUserName -> {
                startActivity(Intent(mActivity, LoginActivity::class.java))
            }
            R.id.mTvCollection -> {
                if (TextUtils.isEmpty(UserHelper.userId)) {
                    startActivity(Intent(mActivity, LoginActivity::class.java))
                }else {
                    startActivity(Intent(mActivity, CollectionActivity::class.java))
                }
            }
            R.id.mTvAbout -> {
                startActivity(Intent(mActivity, AboutActivity::class.java))
            }
        }
    }

    companion object {
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (TextUtils.isEmpty(UserHelper.userId)) {
            mTvUserName.setOnClickListener(this)
        } else {
            mTvUserName.run {
                text = UserHelper.user.username
            }
        }
        mTvCollection.setOnClickListener(this)
        mTvAbout.setOnClickListener(this)
    }

}