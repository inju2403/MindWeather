package com.example.ttogilgi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequestPOJO (
    @SerializedName("username")
    @Expose
    var username: String,
    @SerializedName("password")
    @Expose
    var password: String
)