package com.example.ttogilgi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpRequsetPOJO (
    @SerializedName("username")
    @Expose
    var username: String,
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("password1")
    @Expose
    var password1: String,
    @SerializedName("password2")
    @Expose
    var password2: String
)