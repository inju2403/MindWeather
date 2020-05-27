package com.example.ttogilgi.model.implementations

import com.example.ttogilgi.data.DiaryDao
import com.example.ttogilgi.data.DiaryData
import com.example.ttogilgi.model.repository.IDiaryRepository

class DiaryRepoImpl(val local: DiaryDao) : IDiaryRepository {

    override fun getDiarys() {
        local.getAllDiary()
    }

    override fun getDiaryById(diaryId: String) {
        local.selectDiary(diaryId)
    }

    override fun deleteDiary(diaryId: String) {
        local.deleteDiary(diaryId)
    }

    override fun updateDiary(diary: DiaryData) {
        local.addOrUpdateDiary(diary)
    }
}