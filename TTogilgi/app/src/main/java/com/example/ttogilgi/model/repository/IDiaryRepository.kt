package com.example.ttogilgi.model.repository

import com.example.ttogilgi.model.DiaryData

interface IDiaryRepository {
    fun getDiarys(): List<DiaryData>
    fun getDiaryById(diaryId: String): DiaryData
    fun deleteDiary(diaryId: String)
    fun updateDiary(diary: DiaryData)
}