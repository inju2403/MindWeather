package com.example.ttogilgi.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.DiaryData
import com.example.ttogilgi.model.repository.IDiaryRepository

class ListViewModel(
    val repo: IDiaryRepository
) : ViewModel() {

    val diaryLiveData : MutableLiveData<MutableList<DiaryData>> by lazy {
        MutableLiveData<MutableList<DiaryData>> (repo.getDiarys())
    }
}