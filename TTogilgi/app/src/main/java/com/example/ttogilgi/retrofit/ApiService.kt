package com.example.ttogilgi.retrofit

import com.example.ttogilgi.pojo.LoginRequestPOJO
import com.example.ttogilgi.pojo.Login_SignUP_ReturnPOJO
import com.example.ttogilgi.pojo.SignUpRequsetPOJO
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("auth/registration/") // 회원가입
    fun signUp(@Body signUpRequsetPOJO: SignUpRequsetPOJO) : Call<Login_SignUP_ReturnPOJO>

    @POST("auth/login/") // 로그인
    fun login(@Body loginRequestPOJO: LoginRequestPOJO): Call<Login_SignUP_ReturnPOJO>

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