package com.example.ttogilgi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ttogilgi.login.LoginActivity
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants
import kotlinx.android.synthetic.main.fragment_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    val TAG: String? = "로그"
    val PREFERENCE = "template.android.TTogilgi"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pref = this.activity?.getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        val token = pref?.getString("token", "")

        logoutText.setOnClickListener {
            val httpCall: ApiService?
                    = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)
            httpCall?.logout(token.toString())?.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(TAG,"logout - onFailed() called / t: ${t}")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response!!.code()) {
                        200 -> {
                            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
                            activity?.finish()
                            startActivity(Intent(context, LoginActivity::class.java))
                        }
                        400 -> Toast.makeText(context, "로그아웃 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

            })
        }
    }
}
