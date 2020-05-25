package com.example.ttogilgi.data

import androidx.lifecycle.LiveData

class ListLiveData (private val diraryList: List<DiaryData>): LiveData<List<DiaryData>>() {
    init {
        value = diraryList
    }
}