package com.example.ttogilgi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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

    }
}
