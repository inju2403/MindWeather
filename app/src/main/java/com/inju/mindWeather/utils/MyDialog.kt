package com.inju.mindWeather.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.inju.mindWeather.R

class MyDialog(context : Context) {

    private val RETURN_OK = 101

    private val dialog = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var titleText : TextView
    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var listener : MyDialogOKClickedListener

    fun start(title : String) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(R.layout.dialog_layout)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        titleText = dialog.findViewById(R.id.titleText)
        titleText.text = title

        okButton = dialog.findViewById(R.id.okButton)
        okButton.setOnClickListener {

            //TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드
            listener.onOKClicked(RETURN_OK)

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