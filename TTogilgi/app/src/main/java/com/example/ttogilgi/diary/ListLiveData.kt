package com.example.ttogilgi.diary

import androidx.lifecycle.LiveData
import com.example.ttogilgi.model.DiaryData

class ListLiveData (private val diraryList: List<DiaryData>): LiveData<List<DiaryData>>() {
    init {
        value = diraryList
    }
}