package com.example.ttogilgi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login_SignUp_UserPOJO (
    @SerializedName("pk")
    @Expose
    val pk: Int,
    @SerializedName("username")
    @Expose
    var username: String,
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("first_name")
    @Expose
    var firstName: String,
    @SerializedName("last_name")
    @Expose
    var lastName: String
)