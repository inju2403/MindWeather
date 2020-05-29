package com.example.ttogilgi.diary.diaryList.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.diary.ListViewModel
import com.example.ttogilgi.model.repository.IDiaryRepository

class DiaryListViewModelFactory(
    val diaryRepo: IDiaryRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(diaryRepo) as T
    }
}