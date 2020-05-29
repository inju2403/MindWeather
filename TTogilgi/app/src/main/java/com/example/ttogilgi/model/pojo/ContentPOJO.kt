package com.example.ttogilgi.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContentPOJO (
    @SerializedName("content")
    @Expose
    val content: String
)