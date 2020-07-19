package com.inju.mindWeather.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Diary (
    @SerializedName("id")
    @Expose
    var id: String = "987654321",

    @SerializedName("content")
    @Expose
    var content: String = "",

    @SerializedName("created_at")
    @Expose
    var createdAt: Date = Date(),

    @SerializedName("updated_at")
    @Expose
    var updatedAt: Date = Date(),

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