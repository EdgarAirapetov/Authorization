package com.test.authorization.api.interceptor

import com.test.authorization.utils.PreferenceManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestInterceptor(private var preferenceManager: PreferenceManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return chain.proceed(changeToCurrentUrl(request))
    }

    private fun changeToCurrentUrl(request: Request): Request {
        val url = request.url()
        val httpUrl = HttpUrl.parse(url.toString().replace(url.host(), preferenceManager.domain))
        httpUrl?.let {
            return request.newBuilder().url(it).build()
        }

        throw NullPointerException()
    }
}