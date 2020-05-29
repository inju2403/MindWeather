package com.example.ttogilgi.model.implementations

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.repository.IDiaryRepository
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryRepoImpl(
    val httpCall: ApiService?
    = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java),
    val context: Context
) : IDiaryRepository {

    val PREFERENCE = "template.android.TTogilgi"
    val pref = context.getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
    val token = pref.getString("token", "").toString()

    override fun getDiarys(): List<Diary> {
        lateinit var list: List<Diary>
        httpCall?.getDiarys(token)?.enqueue(object : Callback<List<Diary>> {
            override fun onFailure(call: Call<List<Diary>>, t: Throwable) {
                Log.d(Constants.TAG,"${t}")
            }

            override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {
                when (response!!.code()) {
                    200 -> list = response!!.body() as List<Diary>
                    400 -> Toast.makeText(context, "${response.message()}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return list
    }

    override fun getDiaryById(diaryId: String): Diary {
        lateinit var diary: Diary
        httpCall?.getDiaryById(diaryId.toInt(), token)?.enqueue(object : Callback<Diary> {
            override fun onFailure(call: Call<Diary>, t: Throwable) {
                Log.d(Constants.TAG,"${t}")
            }

            override fun onResponse(call: Call<Diary>, response: Response<Diary>) {
                when (response!!.code()) {
                    200 -> diary = response!!.body() as Diary
                    400 -> Toast.makeText(context, "${response.message()}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return diary
    }

    override fun deleteDiary(diaryId: String) {
        httpCall?.deleteDiary(diaryId.toInt(), token)?.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d(Constants.TAG,"${t}")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when (response!!.code()) {
                    200 -> Toast.makeText(context, "삭제 완료", Toast.LENGTH_LONG).show()
                    400 -> Toast.makeText(context, "${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun updateDiary(diary: Diary) {
        httpCall?.updateDiary(diary, token)?.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d(Constants.TAG,"${t}")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when (response!!.code()) {
                    200 -> Toast.makeText(context, "저장 완료", Toast.LENGTH_LONG).show()
                    400 -> Toast.makeText(context, "${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

        })
    }


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