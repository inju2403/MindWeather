package com.example.ttogilgi.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm

class DetailViewModel : ViewModel() {
    var diaryData = DiaryData()
    val diaryLiveData : MutableLiveData<DiaryData> by lazy {
        MutableLiveData<DiaryData>().apply {
            value = diaryData
        }
    }
    private val realm : Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val diaryDao : DiaryDao by lazy {
        DiaryDao(realm)
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

    fun loadDiary(id: String) {
        diaryData = realm.copyFromRealm(diaryDao.selectDiary(id))
        diaryLiveData.value = diaryData
    }

    fun addOrUpdateDiary(context: Context) {
        diaryDao.addOrUpdateDiary(diaryData)
    }

    fun deleteDiary(id: String) {
        diaryDao.deleteDiary(id)
    }
}