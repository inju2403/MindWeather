package com.example.ttogilgi.diary

import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.DiaryDao
import com.example.ttogilgi.model.DiaryData
import io.realm.Realm

class ListViewModel : ViewModel() {

    private val realm : Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val diaryDao : DiaryDao by lazy {
        DiaryDao(realm)
    }

    val diaryLiveData : RealmLiveData<DiaryData> by lazy {
        RealmLiveData<DiaryData> (diaryDao.getAllDiary())
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }
}