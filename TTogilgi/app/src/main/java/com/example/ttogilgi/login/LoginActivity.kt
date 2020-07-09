package com.example.ttogilgi.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ttogilgi.MainActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.model.pojo.LoginRequestPOJO
import com.example.ttogilgi.model.pojo.Login_SignUP_ReturnPOJO
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.tutorial.TutorialActivity
import com.example.ttogilgi.utils.Constants.API_BASE_URL
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val TAG: String? = "로그"
    val PREFERENCE = "template.android.TTogilgi"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        var runFirst = pref.getBoolean("runFirst", true)

        if(runFirst) { // 최초 실행
            editor.putBoolean("runFirst", false)
            editor.commit()

            startActivity(Intent(this@LoginActivity, TutorialActivity::class.java))
            finish()
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        //비밀번호찾기 텍스트 밑줄 긋기
        var spannableString = SpannableString("비밀번호찾기")
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 1)
        passwordChangeLinkText.text = spannableString

        //비밀번호찾기 리스너
        passwordChangeLinkText.setOnClickListener {

        }

        //자동 로그인
        if(pref.getBoolean("Auto_Login_enabled",false) && !runFirst) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

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
                            editor.putString("token",response.body()?.token)
                            editor.putString("pk",response.body()?.signUpUser?.pk.toString())
                            editor.putString("username",response.body()?.signUpUser?.username)
                            editor.putString("password",passwordEdit.text.toString().trim())
                            editor.putString("email",response.body()?.signUpUser?.email)
                            if(autoLoginCheck.isChecked) {
                                editor.putBoolean("Auto_Login_enabled", true)
                            }
                            editor.commit()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        400 -> Toast.makeText(this@LoginActivity, "계정 정보를 확인해주세요", Toast.LENGTH_LONG).show()
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
