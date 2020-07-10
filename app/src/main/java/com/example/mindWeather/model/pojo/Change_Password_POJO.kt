package com.example.mindWeather.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Change_Password_POJO (
    @SerializedName("new_password1")
    @Expose
    var new_password1: String = "",

    @SerializedName("new_password2")
    @Expose
    var new_password2: String = "",

    @SerializedName("old_password")
    @Expose
    var old_password: String = ""
)