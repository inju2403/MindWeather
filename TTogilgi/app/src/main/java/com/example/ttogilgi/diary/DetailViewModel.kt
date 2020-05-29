package com.example.ttogilgi.diary

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.DiaryData
import com.example.ttogilgi.model.repository.IDiaryRepository

class DetailViewModel (
    val repo: IDiaryRepository
) : ViewModel() {

    var diaryData = DiaryData()
    val diaryLiveData : MutableLiveData<DiaryData> by lazy {
        MutableLiveData<DiaryData>().apply {
            value = diaryData
        }
    }

    fun loadDiary(id: String) {
        diaryData = repo.getDiaryById(id)
        diaryLiveData.value = diaryData
    }

    fun addOrUpdateDiary(context: Context) {
        repo.updateDiary(diaryData)
    }

    fun deleteDiary(id: String) {
        repo.deleteDiary(id)
    }
}