package com.example.ttogilgi.retrofit

import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.pojo.LoginRequestPOJO
import com.example.ttogilgi.model.pojo.Login_SignUP_ReturnPOJO
import com.example.ttogilgi.model.pojo.SignUpRequsetPOJO
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
    fun logout(@Field("Authorization") Authorization: String): Call<Void>



    // 일기 리스트 받기, 일기 받기, 일기 수정, 일기 삭제

    @GET("diary/") // 일기 리스트 받기
    fun getDiarys(@Query("Authorization") Authorization: String): Call<JSONArray>

    @GET("diary/{id}/") // 일기 받기
    fun getDiaryById(@Path("id") id: Int, @Query("Authorization") Authorization: String): Call<Diary>

    @FormUrlEncoded
    @PATCH("diary/") // 일기 수정
    fun updateDiary(@Field("id") id: String, @Query("Authorization") Authorization: String): Call<Void>

    @DELETE("diary/{id}/") // 일기 삭제
    fun deleteDiary(@Path("id") id: Int, @Query("Authorization") Authorization: String): Call<Void>

}