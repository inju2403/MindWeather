package com.inju.mindWeather.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpRequsetPOJO (
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("password1")
    @Expose
    val password1: String,
    @SerializedName("password2")
    @Expose
    val password2: String
)