package com.example.mindWeather.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Change_Username_POJO (
    @SerializedName("username")
    @Expose
    var username: String = ""
)