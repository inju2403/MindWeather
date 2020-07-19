package com.example.mindWeather.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mindWeather.R
import com.example.mindWeather.model.pojo.Change_Username_POJO
import com.example.mindWeather.retrofit.ApiService
import com.example.mindWeather.retrofit.RetrofitClient
import com.example.mindWeather.utils.Constants
import kotlinx.android.synthetic.main.activity_username_change.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsernameChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_change)

        val httpCall: ApiService?
                = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)

        val pref = getSharedPreferences(Constants.PREFERENCE, MODE_PRIVATE)
        val token = "JWT ${pref.getString("token", "").toString()}"

        changeRequestBtn.setOnClickListener {
            httpCall?.changeUsername(token,
                Change_Username_POJO(newUsernameEdit.text.toString().trim())
            )?.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(Constants.TAG,"change username - onFailed() called / t: ${t}")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when(response!!.code()) {
                        200 -> {
                            val pref = applicationContext!!.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.clear()
                            editor.putBoolean("runFirst", false)
                            editor.commit()

                            //로그아웃 시키기
                            var chk = 1
                            httpCall?.logout(token)?.enqueue(object : Callback<Void> {
                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Log.d(Constants.TAG,"logout - onFailed() called / t: ${t}")
                                }

                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    when (response!!.code()) {
                                        200 -> {
                                            val pref = applicationContext!!.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
                                            val editor = pref.edit()
                                            editor.clear()
                                            editor.putBoolean("runFirst", false)
                                            editor.commit()

                                        }
                                        400 -> {
                                            Log.d(Constants.TAG,"닉네임 변경 후 logout - onFailed() called ")
                                            chk=0
                                        }
                                    }
                                }
                            })
                            if(chk==1) {
                                Toast.makeText(this@UsernameChangeActivity, "닉네임 변경 완료. 변경된 닉네임으로 재로그인 해주세요.", Toast.LENGTH_LONG).show()
                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                                finish()
                            }
                            else Toast.makeText(this@UsernameChangeActivity, "닉네임 변경 실패", Toast.LENGTH_LONG).show()
                        }
                        400 -> {
                            Toast.makeText(this@UsernameChangeActivity, "닉네임 변경 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                }

            })
        }

        goBackBtn.setOnClickListener {
            finish()
        }
    }
}
