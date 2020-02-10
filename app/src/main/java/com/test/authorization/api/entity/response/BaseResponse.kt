package com.test.authorization.api.entity.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("error")
    var error: String? = null

    @SerializedName("error_description")
    var errorDescription: String? = null
}