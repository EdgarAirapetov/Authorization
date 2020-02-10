package com.test.authorization.api

import com.test.authorization.api.entity.request.LoginRequest
import com.test.authorization.api.entity.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("oauth/v2/token")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}