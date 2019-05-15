package com.xiocao.wanandroid.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_webview.*

/**
 * User : lijun
 * Date : 2018/3/14  11:01
 * Content : This is
 */
class WebFragment : BaseFragment() {
    companion object {
        fun newInstance(url: String): WebFragment {
            val args = Bundle()
            args.putString("url", url)
            val fragment = WebFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        var settings: WebSettings = mWebView.settings
        settings.run {
            javaScriptEnabled = true//支持js
            useWideViewPort = true//将图片调整到适合webview的大小
            loadWithOverviewMode = true// 缩放至屏幕的大小
            cacheMode = WebSettings.LOAD_NO_CACHE  //关闭webview中缓存
            domStorageEnabled = true
        }
        mWebView.run {
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    if (title != null) {
                        mActivity.setCenterTitle(title)
                    }
                }
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }
            }
            loadUrl(arguments!!.getString("url"))
        }
    }

}
