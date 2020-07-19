package com.example.mindWeather.retrofit

import com.example.mindWeather.model.pojo.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST("auth/registration/") // 회원가입
    fun signUp(@Body signUpRequsetPOJO: SignUpRequsetPOJO): Call<Login_SignUP_ReturnPOJO>

    @POST("auth/login/") // 로그인
    fun login(@Body loginRequestPOJO: LoginRequestPOJO): Call<Login_SignUP_ReturnPOJO>

    @POST("auth/logout/") // 로그아웃
    fun logout(@Header("Authorization") Authorization: String): Call<Void>

    @POST("auth/password/change/") // 비밀번호 변경
    fun changePassword(@Header("Authorization") Authorization: String, @Body changePasswordPOJO: Change_Password_POJO ): Call<Void>

    @PATCH("auth/user/") // 닉네임 변경
    fun changeUsername(@Header("Authorization") Authorization: String, @Body changeUsernamePOJO: Change_Username_POJO): Call<Void>

    @GET("auth/delete/") // 계정 삭제
    fun deleteUser(@Header("Authorization") Authorization: String): Call<Void>

    // 일기 리스트 받기, 일기 받기, 일기 수정, 일기 삭제

    @GET("diary/") // 일기 리스트 받기
    suspend fun getDiarys(@Header("Authorization") Authorization: String): MutableList<Diary>

    @GET("diary/{id}/") // 일기 받기
    suspend fun getDiaryById(@Path("id") id: String, @Header("Authorization") Authorization: String): Diary

    @PATCH("diary/{id}/") // 일기 수정
    suspend fun updateDiary(@Path("id") id: String,
                            @Body contentPOJO: ContentPOJO, @Header("Authorization") Authorization: String): Response<Unit>

//    @PATCH("diary/999999999/") // 일기 수정 임시
//    suspend fun updateDiary(@Body contentPOJO: ContentPOJO, @Header("Authorization") Authorization: String): Response<Unit>

    @DELETE("diary/{id}/") // 일기 삭제
    suspend fun deleteDiary(@Path("id") id: String, @Header("Authorization") Authorization: String): Response<Unit>
}