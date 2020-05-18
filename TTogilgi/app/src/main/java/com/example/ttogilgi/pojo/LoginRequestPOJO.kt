package com.example.ttogilgi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequestPOJO (
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("password")
    @Expose
    val password: String
)