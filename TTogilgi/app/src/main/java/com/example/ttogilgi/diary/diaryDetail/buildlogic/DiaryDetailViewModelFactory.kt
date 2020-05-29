package com.example.ttogilgi.diary.diaryDetail.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.diary.DetailViewModel
import com.example.ttogilgi.model.repository.IDiaryRepository

class DiaryDetailViewModelFactory (
    val diaryRepo: IDiaryRepository
):ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(diaryRepo) as T
    }
}