package com.example.ttogilgi.utils

import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.ttogilgi.R
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants.PREFERENCE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLogoutDialog(context : Context) {

    private val RETURN_OK = 101

    private val dialog = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var titleText : TextView
    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var listener : MyDialogOKClickedListener

    var applicationContext = context

    fun start(title : String, token: String) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(R.layout.dialog_logout)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        titleText = dialog.findViewById(R.id.titleText)
        titleText.text = title

        okButton = dialog.findViewById(R.id.okButton)
        okButton.setOnClickListener {

            //TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드
            val httpCall: ApiService?
                    = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)
            httpCall?.logout(token)?.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(Constants.TAG,"logout - onFailed() called / t: ${t}")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response!!.code()) {
                        200 -> {
                            val pref = applicationContext!!.getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.clear()
                            editor.commit()

                            Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
                            listener.onOKClicked(RETURN_OK)
                        }
                        400 -> Toast.makeText(applicationContext, "로그아웃 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

            })

            dialog.dismiss()
        }

        cancelButton = dialog.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun setOnOKClickedListener(listener: (Int) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(chkNum: Int) {
                listener(chkNum)
            }
        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(chkNum: Int)
    }

}
