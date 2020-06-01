package com.example.ttogilgi.diary

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.repository.IDiaryRepository

class DetailViewModel (
    val repo: IDiaryRepository
) : ViewModel() {

    var diary = Diary()
    val diaryLiveData : MutableLiveData<Diary> by lazy {
        MutableLiveData<Diary>().apply {
            value = diary
        }
    }

    fun loadDiary(id: String) {
        diary = repo.getDiaryById(id)
        diaryLiveData.value = diary
    }

    fun addOrUpdateDiary(context: Context) {
        repo.updateDiary(diary.id)
    }

    fun deleteDiary(id: String) {
        repo.deleteDiary(id)
    }
}