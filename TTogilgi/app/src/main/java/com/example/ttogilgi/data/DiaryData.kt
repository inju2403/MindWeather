package com.example.ttogilgi.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class DiaryData (
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var createdAt: Date = Date(),
    var updatedAt: Date = Date(),
    var content: String = "",
    var summary: String = "",
    var userId: String = "",
    var feeling: Int = 0,
    var qna: String = ""
) : RealmObject()