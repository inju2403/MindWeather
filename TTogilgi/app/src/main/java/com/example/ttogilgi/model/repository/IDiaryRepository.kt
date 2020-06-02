package com.example.ttogilgi.model.repository

import android.content.Context
import com.example.ttogilgi.model.pojo.ContentPOJO
import com.example.ttogilgi.model.pojo.Diary

interface IDiaryRepository {
    suspend fun getDiarys(): MutableList<Diary>
    suspend fun getDiaryById(context: Context, diaryId: String): Diary
    suspend fun updateDiary(context: Context, contentPOJO: ContentPOJO, diaryId: String)
    suspend fun deleteDiary(context: Context, diaryId: String)

//    fun getDiarys(): List<DiaryData>
//    fun getDiaryById(diaryId: String): DiaryData
//    fun deleteDiary(diaryId: String)
//    fun updateDiary(diary: DiaryData)
}