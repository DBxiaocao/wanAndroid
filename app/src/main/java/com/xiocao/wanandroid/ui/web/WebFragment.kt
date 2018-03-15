package com.xiocao.wanandroid.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.orhanobut.logger.Logger
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        var settings: WebSettings = mWebView.settings
        settings.javaScriptEnabled = true  //支持js
        settings.useWideViewPort = true //将图片调整到适合webview的大小
        settings.loadWithOverviewMode = true // 缩放至屏幕的大小
        settings.cacheMode = WebSettings.LOAD_NO_CACHE  //关闭webview中缓存
        settings.domStorageEnabled = true
        mWebView.webChromeClient=object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Logger.e(newProgress.toString())
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (title != null) {
                    mActivity.setCenterTitle(title)
                }
            }
        }
        mWebView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Logger.d("加载结束")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Logger.d("加载开始")
            }
        }
        mWebView.loadUrl(arguments.getString("url"))
    }

}
