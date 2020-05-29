package com.example.ttogilgi.data.diaryDetail.buildLogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ttogilgi.data.DiaryDao
import com.example.ttogilgi.model.implementations.DiaryRepoImpl
import com.example.ttogilgi.model.repository.IDiaryRepository
import io.realm.Realm

class DiaryDetailInjector(application: Application): AndroidViewModel(application) {
    private val realm : Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val diaryDao : DiaryDao by lazy {
        DiaryDao(realm)
    }
    private fun getNoteRepository(): IDiaryRepository {
        return DiaryRepoImpl(diaryDao)
    }

    fun provideDiaryListViewModelFactory(): DiaryViewModelFactory =
        DiaryViewModelFactory(
            getNoteRepository()
        )
}