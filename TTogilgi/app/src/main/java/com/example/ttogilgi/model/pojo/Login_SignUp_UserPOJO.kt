package com.example.ttogilgi.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login_SignUp_UserPOJO (
    @SerializedName("pk")
    @Expose
    val pk: Int,
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("first_name")
    @Expose
    val firstName: String,
    @SerializedName("last_name")
    @Expose
    val lastName: String
)