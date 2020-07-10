package com.example.mindWeather.diary.diaryDetail.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mindWeather.model.implementations.DiaryRepoImpl
import com.example.mindWeather.model.repository.IDiaryRepository
import com.example.mindWeather.retrofit.ApiService
import com.example.mindWeather.retrofit.RetrofitClient
import com.example.mindWeather.utils.Constants

class DiaryDetailInjector(application: Application): AndroidViewModel(application) {
//    private val realm : Realm by lazy {
//        Realm.getDefaultInstance()
//    }
//
//    private val diaryDao : DiaryDao by lazy {
//        DiaryDao(realm)
//    }

    val app = application

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