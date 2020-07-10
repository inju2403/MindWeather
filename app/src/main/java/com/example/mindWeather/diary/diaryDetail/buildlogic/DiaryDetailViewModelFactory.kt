package com.example.mindWeather.diary.diaryDetail.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mindWeather.diary.DetailViewModel
import com.example.mindWeather.model.repository.IDiaryRepository
import kotlinx.coroutines.Dispatchers

class DiaryDetailViewModelFactory (
    private val repo: IDiaryRepository
):ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repo, Dispatchers.Main) as T
    }
}