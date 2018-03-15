package com.xiocao.wanandroid.retrofit

import com.orhanobut.logger.Logger

import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import okio.BufferedSource

/**
 * User : lijun
 * Date : 2018/3/12  16:11
 * Content : This is
 */
class LogInterceptor : Interceptor {

    private val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body()

        var body: String? = null

        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            body = buffer.readString(charset!!)
        }
        Logger.e("=================发送请求==============\nMethod：" + request.method() + "\nUrl："
                + request.url() + "\nHeaders：" + request.headers() + "\nBody：" + body)

        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        var rBody: String? = null

        if (HttpHeaders.hasBody(response)) {
            val source = responseBody!!.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            var charset: Charset? = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    e.printStackTrace()
                }

            }
            rBody = buffer.clone().readString(charset!!)
        }

        Logger.e("=================收到响应==============" + response.code() + "  " + response.message()
                + "  " + tookMs + "ms\n请求Url：" + response.request().url() + "\n请求body：" + body)
        Logger.json(rBody)
        return response
    }
}
