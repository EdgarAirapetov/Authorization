package com.test.authorization.api.entity.response

import com.google.gson.annotations.SerializedName

class LoginResponse: BaseResponse() {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("expires_in")
    var expiresIn: Int? = null

    @SerializedName("token_type")
    var tokenType: String? = null

    @SerializedName("scope")
    var scope: String? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null
}