package com.example.ttogilgi.retrofit

import com.example.ttogilgi.model.pojo.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("auth/registration/") // 회원가입
    fun signUp(@Body signUpRequsetPOJO: SignUpRequsetPOJO): Call<Login_SignUP_ReturnPOJO>

    @POST("auth/login/") // 로그인
    fun login(@Body loginRequestPOJO: LoginRequestPOJO): Call<Login_SignUP_ReturnPOJO>

    @FormUrlEncoded
    @POST("auth/logout/") // 로그아웃
    fun logout(@Header("Authorization") Authorization: String): Call<Void>



    // 일기 리스트 받기, 일기 받기, 일기 수정, 일기 삭제

    @GET("diary/") // 일기 리스트 받기
    fun getDiarys(@Header("Authorization") Authorization: String): Call<JSONArray>

    @GET("diary/{id}/") // 일기 받기
    fun getDiaryById(@Path("id") id: String, @Header("Authorization") Authorization: String): Call<Diary>

    @PATCH("diary/{id}/") // 일기 수정
    fun updateDiary(@Path("id") id: String, @Body contentPOJO: ContentPOJO, @Header("Authorization") Authorization: String): Call<Void>

    @DELETE("diary/{Authorization}/") // 일기 삭제
    fun deleteDiary(@Path("id") id: String, @Header("Authorization") Authorization: String): Call<Void>

}