package com.inju.mindWeather.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContentPOJO (
    @SerializedName("content")
    @Expose
    var content: String = ""
)