package com.xiocao.wanandroid.ui.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.utils.AppUtils
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setCenterTitle("关于")
        setEnabledNavigation(true)
        tvAbout.run {
            text = Html.fromHtml(getString(R.string.about_content))
            movementMethod = LinkMovementMethod.getInstance()
        }
        tvVersion.run {
            text = "v" + AppUtils.getLocalVersionName(mActivity)
        }
    }
}
