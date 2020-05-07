package com.example.ttogilgi.retrofit

import android.util.Log
import com.example.ttogilgi.utils.Constants.API_BASE_URL
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    val TAG : String = "로그"

    companion object {
        val instance = RetrofitManager()
    }

    val httpCall: ApiService?
            = RetrofitClient.getClient(API_BASE_URL)?.create(ApiService::class.java)

    fun postRegister() {
        val call = httpCall?.postRegister()
    }

    fun postLogin() {
        val call = httpCall?.postLogin()
    }

    fun postLogout() {
    }

    fun getDiary() {
        var call = httpCall?.getDiary()
        call?.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"Retrofit Manager - getDiary() - onFailed() called / t: ${t}")
            }
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"Retrofit Manager - getDiary() - onResponse() called / response: ${response.body()}")
            }
        })
    }

    fun postDiary() {
        var call = httpCall?.postDiary()
    }

    fun patchDiary() {
        var call = httpCall?.patchDiary()
    }

    fun deleteDiary() {
        var call = httpCall?.deleteDiary()
    }
}