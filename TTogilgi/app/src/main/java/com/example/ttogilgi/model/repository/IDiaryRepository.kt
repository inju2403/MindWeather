package com.example.ttogilgi.model.repository

import android.content.Context
import com.example.ttogilgi.model.pojo.ContentPOJO
import com.example.ttogilgi.model.pojo.Diary

interface IDiaryRepository {
    fun getDiarys(): MutableList<Diary>
    fun getDiaryById(context: Context, diaryId: String): Diary
    fun updateDiary(context: Context, contentPOJO: ContentPOJO, diaryId: String)
    fun deleteDiary(context: Context, diaryId: String)

//    fun getDiarys(): List<DiaryData>
//    fun getDiaryById(diaryId: String): DiaryData
//    fun deleteDiary(diaryId: String)
//    fun updateDiary(diary: DiaryData)
}