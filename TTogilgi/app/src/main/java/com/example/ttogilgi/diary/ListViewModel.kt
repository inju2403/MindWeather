package com.example.ttogilgi.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.repository.IDiaryRepository

class ListViewModel(
    val repo: IDiaryRepository
) : ViewModel() {

    val diaryList: List<Diary> = repo.getDiarys()

    val diaryListLiveData : MutableLiveData<List<Diary>> by lazy {
        MutableLiveData<List<Diary>>().apply {
            value = diaryList
        }
    }
}