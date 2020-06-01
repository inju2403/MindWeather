package com.example.ttogilgi.model.repository

import com.example.ttogilgi.model.pojo.Diary

interface IDiaryRepository {
    fun getDiarys(): List<Diary>
    fun getDiaryById(diaryId: String): Diary
    fun updateDiary(diaryId: String)
    fun deleteDiary(diaryId: String)

//    fun getDiarys(): List<DiaryData>
//    fun getDiaryById(diaryId: String): DiaryData
//    fun deleteDiary(diaryId: String)
//    fun updateDiary(diary: DiaryData)
}