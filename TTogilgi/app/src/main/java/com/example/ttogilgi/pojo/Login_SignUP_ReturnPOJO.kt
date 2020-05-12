package com.example.ttogilgi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login_SignUP_ReturnPOJO (
    @SerializedName("token")
    @Expose
    var token: String,
    @SerializedName("user")
    @Expose
    var signUpUser: Login_SignUp_UserPOJO
)