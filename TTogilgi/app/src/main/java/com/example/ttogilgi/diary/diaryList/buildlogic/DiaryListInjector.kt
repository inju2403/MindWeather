package com.example.ttogilgi.diary.diaryList.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ttogilgi.model.DiaryDao
import com.example.ttogilgi.model.implementations.DiaryRepoImpl
import com.example.ttogilgi.model.repository.IDiaryRepository
import io.realm.Realm

class DiaryListInjector(application: Application): AndroidViewModel(application) {

    private val realm : Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val diaryDao : DiaryDao by lazy {
        DiaryDao(realm)
    }

    private fun getDiaryRepository(): IDiaryRepository {
        return DiaryRepoImpl(diaryDao)
    }

    fun provideDiaryListViewModelFactory(): DiaryListViewModelFactory =
        DiaryListViewModelFactory(
            getDiaryRepository()
        )

}