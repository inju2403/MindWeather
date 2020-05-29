package com.example.ttogilgi.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.repository.IDiaryRepository

class DetailViewModel (
    val repo: IDiaryRepository
) : ViewModel() {

    var diaryData = DiaryData()
    val diaryLiveData : MutableLiveData<DiaryData> by lazy {
        MutableLiveData<DiaryData>().apply {
            value = diaryData
        }
    }
//    private val realm : Realm by lazy {
//        Realm.getDefaultInstance()
//    }
//
//    private val diaryDao : DiaryDao by lazy {
//        DiaryDao(realm)
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        realm.close()
//    }

    fun loadDiary(id: String) {
        diaryData = repo.getDiaryById(id)
        diaryLiveData.value = diaryData
//        diaryData = realm.copyFromRealm(diaryDao.selectDiary(id))
//        diaryLiveData.value = diaryData
    }

    fun addOrUpdateDiary(context: Context) {
        repo.updateDiary(diaryData)
//        diaryDao.addOrUpdateDiary(diaryData)
    }

    fun deleteDiary(id: String) {
        repo.deleteDiary(id)
//        diaryDao.deleteDiary(id)
    }
}