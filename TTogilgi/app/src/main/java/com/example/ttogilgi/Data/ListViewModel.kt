package com.example.ttogilgi.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    private val diarys: MutableList<DiaryData> = mutableListOf()
    val diaryLiveData: MutableLiveData<MutableList<DiaryData>> by lazy {
        MutableLiveData<MutableList<DiaryData>>().apply {
            value = diarys
        }
    }

    fun addDiary(data: DiaryData) {
        val tmpList = diaryLiveData.value
        tmpList?.add(data)
        diaryLiveData.value = tmpList
    }
}