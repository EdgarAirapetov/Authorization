package com.test.authorization.api.entity.request

import com.google.gson.annotations.SerializedName

class LoginRequest : BaseRequest() {
    @SerializedName("username")
    var username: String? = null

    @SerializedName("password")
    var password: String? = null
}