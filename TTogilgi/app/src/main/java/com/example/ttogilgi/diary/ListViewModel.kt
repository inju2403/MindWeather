package com.example.ttogilgi.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.repository.IDiaryRepository

class ListViewModel(
    val repo: IDiaryRepository
) : ViewModel() {

    val diaryListLiveData : MutableLiveData<MutableList<Diary>> by lazy {
        MutableLiveData<MutableList<Diary>>().apply {
            value = repo.getDiarys()
        }
    }
}