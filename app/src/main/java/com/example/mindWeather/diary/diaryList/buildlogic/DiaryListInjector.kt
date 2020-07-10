package com.example.mindWeather.diary.diaryList.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mindWeather.model.implementations.DiaryRepoImpl
import com.example.mindWeather.model.repository.IDiaryRepository
import com.example.mindWeather.retrofit.ApiService
import com.example.mindWeather.retrofit.RetrofitClient
import com.example.mindWeather.utils.Constants

class DiaryListInjector(application: Application): AndroidViewModel(application) {

    val app: Application = application

    private val httpCall: ApiService?
            = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)

    private fun getDiaryRepository(): IDiaryRepository {
        return DiaryRepoImpl(httpCall, app)
    }

    fun provideDiaryListViewModelFactory(): DiaryListViewModelFactory =
        DiaryListViewModelFactory(
            getDiaryRepository()
        )

}