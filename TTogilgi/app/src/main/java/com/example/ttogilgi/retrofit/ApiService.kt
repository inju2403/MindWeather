package com.example.ttogilgi.retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @POST("auth/registration/")
    fun postRegister()

    @POST("auth/login/")
    fun postLogin()

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