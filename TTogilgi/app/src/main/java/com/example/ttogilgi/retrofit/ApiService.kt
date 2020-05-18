package com.example.ttogilgi.retrofit

import com.example.ttogilgi.pojo.ContentPOJO
import com.example.ttogilgi.pojo.LoginRequestPOJO
import com.example.ttogilgi.pojo.Login_SignUP_ReturnPOJO
import com.example.ttogilgi.pojo.SignUpRequsetPOJO
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

    @FormUrlEncoded
    @GET("diary/{pk}/") // 일기 조회
    fun getDiary(@Path("pk") pk: Int, @Field("Authorization") Authorization: String): Call<ContentPOJO>

    @FormUrlEncoded
    @POST("diary/") // 일기 쓰기
    fun postDiary(@Path("pk") pk: Int, @Field("Authorization") Authorization: String): Call<ContentPOJO>

    @FormUrlEncoded
    @PATCH("diary/{pk}/") // 일기 수정
    fun patchDiary(@Path("pk") pk: Int, @Field("Authorization") Authorization: String): Call<ContentPOJO>

    @FormUrlEncoded
    @DELETE("diary/{pk}/") // 일기 삭제
    fun deleteDiary(@Path("pk") pk: Int, @Field("Authorization") Authorization: String): Call<Void>
}