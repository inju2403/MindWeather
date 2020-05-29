package com.example.ttogilgi.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.MainActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.model.pojo.LoginRequestPOJO
import com.example.ttogilgi.model.pojo.Login_SignUP_ReturnPOJO
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

        loginBtn.setOnClickListener {
            val httpCall: ApiService?
                    = RetrofitClient.getClient(API_BASE_URL)!!.create(ApiService::class.java)

            httpCall?.login(LoginRequestPOJO(usernameEdit.text.toString().trim(),
                passwordEdit.text.toString().trim()))?.enqueue(object :
                Callback<Login_SignUP_ReturnPOJO> {
                override fun onFailure(call: Call<Login_SignUP_ReturnPOJO>, t: Throwable) {
                    Log.d(TAG,"login - onFailed() called / t: ${t}")
                }

                override fun onResponse(call: Call<Login_SignUP_ReturnPOJO>, response: Response<Login_SignUP_ReturnPOJO>) {
                    when (response!!.code()) {
                        200 -> {
                            val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putString("token",response.body()?.token)
                            editor.putString("pk",response.body()?.signUpUser?.pk.toString())
                            editor.putString("username",response.body()?.signUpUser?.username)
                            editor.putString("email",response.body()?.signUpUser?.email)
                            editor.commit()
                            finish()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                        400 -> Toast.makeText(this@LoginActivity, "로그인 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
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
