package com.example.ttogilgi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login_SignUP_ReturnPOJO (
    @SerializedName("token")
    @Expose
    val token: String,
    @SerializedName("user")
    @Expose
    val signUpUser: Login_SignUp_UserPOJO
)