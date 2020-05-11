package com.example.ttogilgi.retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("auth/registration/") // 회원가입
    @FormUrlEncoded
    fun signUp(@Field("username") username: String, @Field("password") password: String): Call<Void>

    @POST("auth/login/") // 로그인
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Call<Void>

    @POST("auth/logout/")
    fun postLogout()

    @GET("diary/")
    fun getDiary(): Call<JsonElement>

    @POST("diary/")
    fun postDiary()

    @PATCH("diary/{pk}/")
    fun patchDiary()

    @DELETE("diary/{pk}/")
    fun deleteDiary()
}