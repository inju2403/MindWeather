package com.example.ttogilgi.diary

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.pojo.ContentPOJO
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

    fun loadDiary(context: Context, id: String) {
        diary = repo.getDiaryById(context, id)
        diaryLiveData.value = diary
    }

    fun addOrUpdateDiary(context: Context) {
        val contentPOJO = ContentPOJO(diary.content)
        repo.updateDiary(context, contentPOJO,  diary.id)
    }

    fun deleteDiary(context: Context, id: String) {
        repo.deleteDiary(context, id)
    }
}