package com.example.ttogilgi.data.diaryDetail.buildLogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.data.DetailViewModel
import com.example.ttogilgi.model.repository.IDiaryRepository

class DiaryViewModelFactory (
    val diaryRepo: IDiaryRepository
):ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(diaryRepo) as T
    }
}