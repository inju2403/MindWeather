package com.inju.mindWeather.diary.diaryList.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.inju.mindWeather.model.implementations.DiaryRepoImpl
import com.inju.mindWeather.model.repository.IDiaryRepository
import com.inju.mindWeather.retrofit.ApiService
import com.inju.mindWeather.retrofit.RetrofitClient
import com.inju.mindWeather.utils.Constants

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