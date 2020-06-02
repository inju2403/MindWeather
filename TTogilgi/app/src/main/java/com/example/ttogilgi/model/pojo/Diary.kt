package com.example.ttogilgi.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Diary (
    @SerializedName("id")
    @Expose
    var id: String = "987654321",

    @SerializedName("content")
    @Expose
    var content: String = "",

    @SerializedName("createdAt")
    @Expose
    var createdAt: String = "",

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String = "",

    @SerializedName("happiness")
    @Expose
    var happiness: Int = 0,

    @SerializedName("neutrality")
    @Expose
    var neutrality: Int = 0,

    @SerializedName("sadness")
    @Expose
    var sadness: Int = 0,

    @SerializedName("worry")
    @Expose
    var worry: Int = 0,

    @SerializedName("anger")
    @Expose
    var anger: Int = 0,

    @SerializedName("qna")
    @Expose
    var qna: String = "",

    @SerializedName("user")
    @Expose
    var user: String = ""
)