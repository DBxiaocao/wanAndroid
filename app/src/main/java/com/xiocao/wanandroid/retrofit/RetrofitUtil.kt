package com.xiocao.wanandroid.retrofit

import com.xiocao.wanandroid.app.ApiUrl
import com.xiocao.wanandroid.retrofit.tools.Preference
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * description: RetrofitUtil
 * author: lijun
 * date: 17/11/30 11:02
 */

class RetrofitUtil private constructor() {

    fun retrofit(api: String): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(ApiUrl.Url_Head)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {

        private var mInstance: RetrofitUtil? = null

        val instance: RetrofitUtil
            get() {
                if (mInstance == null) {
                    synchronized(RetrofitUtil::class.java) {
                        if (mInstance == null) {
                            mInstance = RetrofitUtil()
                        }
                    }
                }
                return this.mInstance!!
            }

        fun okHttpClient(): OkHttpClient {
            val BASE_URL= "http://www.wanandroid.com/"
            val SAVE_USER_LOGIN_KEY = "user/login"
            val SAVE_USER_REGISTER_KEY = "user/register"

            val COLLECTIONS_WEBSITE = "lg/collect"
            val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
            val ARTICLE_WEBSITE = "article"

            val SET_COOKIE_KEY = "set-cookie"
            val COOKIE_NAME = "Cookie"
            return OkHttpClient.Builder()
                    .addInterceptor(LogInterceptor())
                    .readTimeout(30, TimeUnit.SECONDS)//设置超时
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor {
                        val request = it.request()
                        val response = it.proceed(request)
                        val requestUrl = request.url().toString()
                        val domain = request.url().host()
                        if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(SAVE_USER_REGISTER_KEY))
                                && !response.headers(SET_COOKIE_KEY).isEmpty()) {
                            val cookies = response.headers(SET_COOKIE_KEY)
                            val cookie = encodeCookie(cookies)
                            saveCookie(requestUrl, domain, cookie)
                        }
                        response
                    }
                    .addInterceptor {
                        val request = it.request()
                        val builder = request.newBuilder()
                        val domain = request.url().host()
                        val url = request.url().toString()
                        if (domain.isNotEmpty() && (url.contains(COLLECTIONS_WEBSITE) || url.contains(UNCOLLECTIONS_WEBSITE) || url.contains(ARTICLE_WEBSITE))) {
                            val spDomain: String by Preference(domain, "")
                            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
                            if (cookie.isNotEmpty()) {
                                builder.addHeader(COOKIE_NAME, cookie)
                            }
                        }
                        it.proceed(builder.build())
                    }
                    .retryOnConnectionFailure(true)
                    .build()
        }

        private fun saveCookie(url: String?, domain: String?, cookies: String) {
            url ?: return
            var spUrl: String by Preference(url, cookies)
            @Suppress("UNUSED_VALUE")
            spUrl = cookies
            domain ?: return
            var spDomain: String by Preference(domain, cookies)
            @Suppress("UNUSED_VALUE")
            spDomain = cookies
        }
        private fun encodeCookie(cookies: List<String>): String {
            val sb = StringBuilder()
            val set = HashSet<String>()
            cookies
                    .map { cookie ->
                        cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    }
                    .forEach {
                        it.filterNot { set.contains(it) }.forEach { set.add(it) }
                    }

            val ite = set.iterator()
            while (ite.hasNext()) {
                val cookie = ite.next()
                sb.append(cookie).append(";")
            }

            val last = sb.lastIndexOf(";")
            if (sb.length - 1 == last) {
                sb.deleteCharAt(last)
            }

            return sb.toString()
        }
    }
}
