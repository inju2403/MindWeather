package com.example.ttogilgi.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    val TAG: String? = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var userName = idEdit.text.toString().trim()
        var password = passwordEdit.text.toString().trim()
        var passwordChk = passwordCheckEdit.text.toString().trim()

        signUpRequestBtn.setOnClickListener {
            val httpCall: ApiService?
                    = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)

            if(password != passwordChk) {
                Toast.makeText(this@SignUpActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
            }
            else {
                httpCall?.signUp(userName, password)?.enqueue(object :
                    Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d(TAG,"signUp - onFailed() called / t: ${t}")
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response!!.code()) {
                            200 -> {
                                Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                                finish ()
                            }
                            405 -> Toast.makeText(this@SignUpActivity, "이미 존재하는 닉네임 입니다", Toast.LENGTH_LONG).show()
                            500 -> Toast.makeText(this@SignUpActivity, "회원가입 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }
}
