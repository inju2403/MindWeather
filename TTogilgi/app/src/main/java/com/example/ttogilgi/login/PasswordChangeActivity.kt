package com.example.ttogilgi.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.model.pojo.Change_Password_POJO
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants
import com.example.ttogilgi.utils.Constants.PREFERENCE
import kotlinx.android.synthetic.main.activity_password_change.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_change)

        val httpCall: ApiService?
                = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)

        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val token = "JWT ${pref.getString("token", "").toString()}"
        val password = pref.getString("password", "")

        changeRequestBtn.setOnClickListener {
            if(password != currentPasswordEdit.text.toString().trim()) {
                Toast.makeText(this@PasswordChangeActivity, "현재 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show()
            }
            else if(newPassword1Edit.text.toString().trim() != newPassword2Edit.text.toString().trim()) {
                Toast.makeText(this@PasswordChangeActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            }
            else if(newPassword1Edit.text.toString().trim().length<8 || newPassword2Edit.text.toString().trim().length<8) {
                Toast.makeText(this@PasswordChangeActivity, "비밀번호를 영문, 숫자, 특수문자를 혼합하여 8자 이상 입력해주세요.", Toast.LENGTH_LONG).show()
            }

            else {
                httpCall?.changePassword(
                    token, Change_Password_POJO(
                        newPassword1Edit.text.toString().trim(),
                        newPassword2Edit.text.toString().trim(),
                        currentPasswordEdit.text.toString().trim()
                    )
                )?.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d(Constants.TAG, "password - onFailed() called / t: ${t}")
                        Toast.makeText(
                            this@PasswordChangeActivity,
                            "비밀번호 변경 실패",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response!!.code()) {
                            200 -> {
                                Toast.makeText(
                                    this@PasswordChangeActivity,
                                    "비밀번호 변경 완료",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                            400 -> {
                                Toast.makeText(
                                    this@PasswordChangeActivity,
                                    "비밀번호 변경 실패",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
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
