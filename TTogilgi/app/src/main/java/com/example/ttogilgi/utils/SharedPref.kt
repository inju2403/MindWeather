package com.example.ttogilgi.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPref {
    val TAG: String? = "로그"
    val LOGIN_SESSION = "login.session"

    private var sharedPref: SharedPreferences? = null

    fun openSharedPrep(context: Context) {
        sharedPref = context.getSharedPreferences(
            LOGIN_SESSION, Context.MODE_PRIVATE)
    }
    fun writeLoginSession(data: String) {
        if(sharedPref == null) {
            Log.e(TAG, "Plz start openSahredPrep() !")
        } else {
            sharedPref?.edit()?.putString("session", data)?.apply()
        }
    }

    fun readLoginSession() : String? {
        return if(sharedPref == null) {
            Log.e(TAG, "Plz start openSahredPrep() !")
            null
        } else sharedPref?.getString("session", null)
    }
}