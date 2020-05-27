package com.example.ttogilgi.model.repository

import com.example.ttogilgi.data.DiaryData

interface IDiaryRepository {
    fun getDiarys()
    fun getDiaryById(diaryId: String)
    fun deleteDiary(diaryId: String)
    fun updateDiary(diary: DiaryData)
}