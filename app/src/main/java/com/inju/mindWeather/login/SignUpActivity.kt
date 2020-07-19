package com.inju.mindWeather.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inju.mindWeather.R
import com.inju.mindWeather.model.pojo.Login_SignUP_ReturnPOJO
import com.inju.mindWeather.model.pojo.SignUpRequsetPOJO
import com.inju.mindWeather.retrofit.ApiService
import com.inju.mindWeather.retrofit.RetrofitClient
import com.inju.mindWeather.utils.Constants
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    val TAG: String? = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpRequestBtn.setOnClickListener {
            if(password1Edit.text.toString().trim() != password2Edit.text.toString().trim()) {
                Toast.makeText(this@SignUpActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            }
            else if(password1Edit.text.toString().trim().length<8 || password1Edit.text.toString().trim().length<8) {
                Toast.makeText(this@SignUpActivity, "비밀번호를 영문, 숫자, 특수문자를 혼합하여 8자 이상 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else {
                val httpCall: ApiService?
                        = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)

                httpCall?.signUp(SignUpRequsetPOJO(usernameEdit.text.toString().trim(),
                    emailEdit.text.toString().trim()
                    ,password1Edit.text.toString().trim(),
                    password2Edit.text.toString().trim()))?.enqueue(object :
                    Callback<Login_SignUP_ReturnPOJO> {
                    override fun onFailure(call: Call<Login_SignUP_ReturnPOJO>, t: Throwable) {
                        Log.d(TAG,"signUp - onFailed() called / t: ${t}")
                    }

                    override fun onResponse(call: Call<Login_SignUP_ReturnPOJO>, response: Response<Login_SignUP_ReturnPOJO>) {
                        when (response!!.code()) {
                            201 -> {
                                Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                                finish ()
                            }
                            400 ->  Toast.makeText(this@SignUpActivity, "이미 존재하는 닉네임 혹은 이메일이거나 잘못된 이메일 형식입니다", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        goBackBtn.setOnClickListener {
            finish()
        }
    }
}
