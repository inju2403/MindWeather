package com.example.mindWeather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mindWeather.diary.ListViewModel
import com.example.mindWeather.diary.diaryList.ListFragment
import com.example.mindWeather.diary.diaryList.buildlogic.DiaryListInjector
import com.example.mindWeather.graph.*
import com.example.mindWeather.login.LoginActivity
import com.example.mindWeather.login.PasswordChangeActivity
import com.example.mindWeather.login.UsernameChangeActivity
import com.example.mindWeather.retrofit.ApiService
import com.example.mindWeather.retrofit.RetrofitClient
import com.example.mindWeather.utils.Constants
import com.example.mindWeather.utils.Constants.PREFERENCE
import com.example.mindWeather.utils.MyDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : NavigationView.OnNavigationItemSelectedListener, AppCompatActivity() {

    private val RETURN_OK = 101

    private var viewModel: ListViewModel? = null
    private var emotionViewModel_1week: EmotionViewModel1week? = null
    private var emotionViewModel_1month: EmotionViewModel1month? = null
    private var emotionViewModel_6month: EmotionViewModel6month? = null
    private var emotionViewModel_1year: EmotionViewModel1year? = null

    private val FRAG_LIST = 0
    private val FRAG_GRAPH = 1

    private lateinit var token: String
    private lateinit var username: String
    private lateinit var email: String

    private val listFragment: ListFragment =
        ListFragment()
    private val graphFragment: GraphFragment =
        GraphFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainActivityToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_open_menu)
        nav_view.setNavigationItemSelectedListener(this) //navigation 리스너

        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        token = "JWT ${pref.getString("token", "").toString()}"
        username = pref.getString("username", "").toString()
        email = pref.getString("email", "").toString()

        Toast.makeText(this@MainActivity, "${username}님 반갑습니다 :)", Toast.LENGTH_LONG).show()
        Log.d("토큰",token)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        val drawerLayout_text = headerView.findViewById<TextView>(R.id.drawerLayout_textView)
        drawerLayout_text.text = "마음의 날씨"

        val username_text = headerView.findViewById<TextView>(R.id.username_text)
        username_text.text = username

        val email_text = headerView.findViewById<TextView>(R.id.email_text)
        email_text.text = email

        setFragment()
        setBottomNavigation()

        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, DiaryListInjector(
                this.application
            ).provideDiaryListViewModelFactory())
                .get(ListViewModel::class.java)
        }

        emotionViewModel_1week = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel1week::class.java)
        }
        emotionViewModel_1month = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel1month::class.java)
        }
        emotionViewModel_6month = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel6month::class.java)
        }
        emotionViewModel_1year = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel1year::class.java)
        }

    }

    private fun setFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.contentFrame, listFragment).commit()

        fragmentManager.beginTransaction().add(R.id.contentFrame, graphFragment).commit()
        fragmentManager.beginTransaction().hide(graphFragment).commit()
    }

    private fun setBottomNavigation() {
        bottomBar.setOnNavigationItemReselectedListener {

        }
        bottomBar.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.list_tab -> {
                        switchFragement(FRAG_LIST)
                        true
                    }
                    R.id.graph_tap -> {
                        switchFragement(FRAG_GRAPH)
                        true
                    }
                    else -> false
                }
            }
        )
    }

    private fun switchFragement(next: Int) {
        val fragmentManager = supportFragmentManager
        when (next) {
            FRAG_LIST -> {
                fragmentManager.beginTransaction().show(listFragment).commit()
                fragmentManager.beginTransaction().hide(graphFragment).commit()
            }
            FRAG_GRAPH -> {
                fragmentManager.beginTransaction().hide(listFragment).commit()
                fragmentManager.beginTransaction().show(graphFragment).commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(Gravity.LEFT)
                drawerLayout.let {
                    //왼쪽
                    if (it.isDrawerOpen(GravityCompat.START)) {
                        it.closeDrawer(GravityCompat.START)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_weather_description -> {
                val intent = Intent(applicationContext, WeatherDescriptionActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_username_edit-> {
                val intent = Intent(applicationContext, UsernameChangeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_password_edit-> {
                val intent = Intent(applicationContext, PasswordChangeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout-> {

                val dialog = MyDialog(this)
                dialog.start("로그아웃 하시겠습니까?")
                dialog.setOnOKClickedListener {
                    if(it == RETURN_OK) {
                        val httpCall: ApiService?
                                = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)
                        httpCall?.logout(token)?.enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d(Constants.TAG,"logout - onFailed() called / t: ${t}")
                            }

                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                when (response!!.code()) {
                                    200 -> {
                                        val pref = applicationContext!!.getSharedPreferences(PREFERENCE,
                                            Context.MODE_PRIVATE
                                        )
                                        val editor = pref.edit()
                                        editor.clear()
                                        editor.putBoolean("runFirst", false)
                                        editor.commit()

                                        Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
                                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                                        finish()
                                    }
                                    400 -> Toast.makeText(applicationContext, "로그아웃 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
                                }
                            }

                        })
                    }
                }
            }
            R.id.nav_delete_user -> {
                val dialog = MyDialog(this)
                dialog.start("계정을 삭제 하시겠습니까?")
                dialog.setOnOKClickedListener {
                    if (it == RETURN_OK) {
                        val dialog2 = MyDialog(this)
                        dialog2.start("모든 계정 정보가 삭제됩니다")
                        dialog2.setOnOKClickedListener {
                            if (it == RETURN_OK) {
                                val httpCall: ApiService? =
                                    RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)
                                httpCall?.deleteUser(token)?.enqueue(object : Callback<Void> {
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Log.d(Constants.TAG, "deleteUser - onFailed() called / t: ${t}")
                                    }

                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        when (response!!.code()) {
                                            200 -> {
                                                val pref =
                                                    applicationContext!!.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
                                                val editor = pref.edit()
                                                editor.clear()
                                                editor.putBoolean("runFirst", false)
                                                editor.commit()
                                                Toast.makeText(applicationContext, "계정이 삭제되었습니다.", Toast.LENGTH_LONG).show()
                                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                                                finish()
                                            }
                                            401 -> Toast.makeText(applicationContext, "계정 삭제 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
        return false
    }

}
