package com.inju.mindWeather.model.implementations

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.inju.mindWeather.model.pojo.ContentPOJO
import com.inju.mindWeather.model.pojo.Diary
import com.inju.mindWeather.model.repository.IDiaryRepository
import com.inju.mindWeather.retrofit.ApiService
import com.inju.mindWeather.retrofit.RetrofitClient
import com.inju.mindWeather.utils.Constants


class DiaryRepoImpl(
    val httpCall: ApiService?
    = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java),
    val context: Context
) : IDiaryRepository {

    val PREFERENCE = "template.android.TTogilgi"
    val pref = context.getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
    val str = pref.getString("token", "").toString()
    val token = "JWT $str"


    override suspend fun getDiarys(): MutableList<Diary> {
        try {
            return httpCall?.getDiarys(token)!!
        }catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun getDiaryById(context: Context, diaryId: String): Diary {
        try {
            return httpCall?.getDiaryById(diaryId, token)!!
        }catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun deleteDiary(context: Context, diaryId: String) {
        try {
            httpCall?.deleteDiary(diaryId, token)
        }catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun updateDiary(context: Context, contentPOJO: ContentPOJO, diaryId: String) {
        try {
            httpCall?.updateDiary(diaryId, contentPOJO, token)
        }catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    //임시 업데이트
//    override suspend fun updateDiary(context: Context, contentPOJO: ContentPOJO, diaryId: String) {
//        httpCall?.updateDiary(contentPOJO, token)
//    }


//    //local
//    override fun getDiarys(): List<DiaryData> {
//        return local.getAllDiary()
//    }
//
//    override fun getDiaryById(diaryId: String): DiaryData {
//        return local.selectDiary(diaryId)
//    }
//
//    override fun deleteDiary(diaryId: String) {
//        local.deleteDiary(diaryId)
//    }
//
//    override fun updateDiary(diary: DiaryData) {
//        local.addOrUpdateDiary(diary)
//    }
}