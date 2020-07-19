package com.inju.mindWeather.diary.diaryList.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inju.mindWeather.diary.ListViewModel
import com.inju.mindWeather.model.repository.IDiaryRepository
import kotlinx.coroutines.Dispatchers

class DiaryListViewModelFactory(
    private val repo: IDiaryRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repo, Dispatchers.Main) as T
    }
}