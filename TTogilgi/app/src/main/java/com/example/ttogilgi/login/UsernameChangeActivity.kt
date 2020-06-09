package com.example.ttogilgi.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttogilgi.R
import com.example.ttogilgi.model.pojo.Change_Username_POJO
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants
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
                    Log.d(Constants.TAG,"password - onFailed() called / t: ${t}")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when(response!!.code()) {
                        200 -> {
                            Toast.makeText(this@UsernameChangeActivity, "닉네임 변경 완료", Toast.LENGTH_LONG).show()
                            finish()
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
