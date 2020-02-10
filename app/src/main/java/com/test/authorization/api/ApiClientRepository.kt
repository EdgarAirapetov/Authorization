package com.test.authorization.api

import android.text.format.DateUtils
import com.test.authorization.BuildConfig
import com.test.authorization.api.entity.request.LoginRequest
import com.test.authorization.api.interceptor.RequestInterceptor
import com.test.authorization.utils.DEFAULT_DOMAIN
import com.test.authorization.utils.PreferenceManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientRepository(private var preferenceManager: PreferenceManager) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(DEFAULT_DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()

    private var apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun login(loginRequest: LoginRequest) =
        apiService.login(loginRequest)

    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        client.addInterceptor(interceptor)
        client.addInterceptor(RequestInterceptor(preferenceManager))
        return client.build()
    }

    companion object Factory {

        private lateinit var instance: ApiClientRepository

        @Synchronized
        fun getInstance(preferenceManager: PreferenceManager): ApiClientRepository {
            if (!::instance.isInitialized)
                instance = ApiClientRepository(preferenceManager)

            return instance
        }
    }
}