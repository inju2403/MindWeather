package com.example.ttogilgi.Data

import java.util.*

class DiaryData (
    //@PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var createdAt: Date = Date(),
    var updatedAt: Date = Date(),
    var title: String = "",
    var content: String = "",
    var summary: String = "",
    var userId: String = "",
    var feeling: Int = 0,
    var qna: String = ""
)