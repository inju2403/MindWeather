package com.example.ttogilgi.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants.API_BASE_URL
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val TAG: String? = "로그"
    val PREFERENCE = "template.android.TTogilgi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username = idEdit.text.toString().trim()
        var password = passwordEdit.text.toString().trim()

        loginBtn.setOnClickListener {
            val httpCall: ApiService?
                    = RetrofitClient.getClient(API_BASE_URL)!!.create(ApiService::class.java)

            httpCall?.login(username, password)?.enqueue(object :
                Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(TAG,"login - onFailed() called / t: ${t}")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response!!.code()) {
                        200 -> {
                            val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putString("username", username)
                            editor.commit()
                            finish()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }

                        305 -> Toast.makeText(this@LoginActivity, "존재하지 않는 닉네임 입니다", Toast.LENGTH_LONG).show()
                        405 -> Toast.makeText(this@LoginActivity, "비밀번호가 올바르지 않습니다", Toast.LENGTH_LONG).show()
                        500 -> Toast.makeText(this@LoginActivity, "로그인 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                    }
                }

            })
        }

        signUpBtn.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
