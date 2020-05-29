package com.example.ttogilgi.diary.diaryDetail.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ttogilgi.model.implementations.DiaryRepoImpl
import com.example.ttogilgi.model.repository.IDiaryRepository
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants

class DiaryDetailInjector(application: Application): AndroidViewModel(application) {
//    private val realm : Realm by lazy {
//        Realm.getDefaultInstance()
//    }
//
//    private val diaryDao : DiaryDao by lazy {
//        DiaryDao(realm)
//    }

    val app: Application = application

    val httpCall: ApiService?
            = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)

    private fun getDiaryRepository(): IDiaryRepository {
        return DiaryRepoImpl(httpCall, app)
    }

    fun provideDiaryViewModelFactory(): DiaryDetailViewModelFactory =
        DiaryDetailViewModelFactory(
            getDiaryRepository()
        )
}