package com.example.ttogilgi.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.pojo.Login_SignUP_ReturnPOJO
import com.example.ttogilgi.pojo.SignUpRequsetPOJO
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

        signUpRequestBtn.setOnClickListener {
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
                        400 ->  Toast.makeText(this@SignUpActivity, "회원가입 실패: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        goBackBtn.setOnClickListener {
            finish()
        }
    }
}
