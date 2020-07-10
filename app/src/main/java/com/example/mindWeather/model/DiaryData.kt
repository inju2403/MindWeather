package com.example.mindWeather.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class DiaryData (
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var createdAt: Date = Date(),
    var updatedAt: Date = Date(),
    var content: String = "",
    var happiness: Int = 0,
    var neutrality: Int = 0,
    var sadness: Int = 0,
    var worry: Int = 0,
    var anger: Int = 0,
    var qna: String = "",
    var user:Int = 0
) : RealmObject()