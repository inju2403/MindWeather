package com.example.ttogilgi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//RetrofitClient Class with Singleton Pattern
object RetrofitClient {

    //레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String) : Retrofit? {

        if(retrofitClient==null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofitClient
    }
}