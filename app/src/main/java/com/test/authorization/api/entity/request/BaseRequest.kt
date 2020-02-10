package com.test.authorization.api.entity.request

import com.google.gson.annotations.SerializedName

open class BaseRequest {
    @SerializedName("grant_type")
    val grant_type = "password"

    @SerializedName("client_id")
    val client_id = "h3jsuowjpfgg8swocks4gix9k4wkwqn08so0ww084wwj2i3sucx"

    @SerializedName("client_secret")
    val client_secret = "n5oxn2swi9sko4883gsj3hwx9eko4og88wks0gkooo42h3hsa9c"
}