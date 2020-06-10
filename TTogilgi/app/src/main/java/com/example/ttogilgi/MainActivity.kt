package com.example.ttogilgi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.diary.ListViewModel
import com.example.ttogilgi.diary.diaryList.ListFragment
import com.example.ttogilgi.diary.diaryList.buildlogic.DiaryListInjector
import com.example.ttogilgi.graph.EmotionViewModel
import com.example.ttogilgi.graph.GraphFragment
import com.example.ttogilgi.login.LoginActivity
import com.example.ttogilgi.login.PasswordChangeActivity
import com.example.ttogilgi.login.UsernameChangeActivity
import com.example.ttogilgi.retrofit.ApiService
import com.example.ttogilgi.retrofit.RetrofitClient
import com.example.ttogilgi.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : NavigationView.OnNavigationItemSelectedListener, AppCompatActivity() {

    val PREFERENCE = "template.android.TTogilgi"

    private var viewModel: ListViewModel? = null
    private var emotionViewModel: EmotionViewModel? = null

    private val FRAG_LIST = 0
    private val FRAG_GRAPH = 1
    private val FRAG_SETTING = 2

    private lateinit var token: String
    private lateinit var username: String

    private val listFragment: ListFragment =
        ListFragment()
    private val graphFragment: GraphFragment =
        GraphFragment()
    private val settingFragment: SettingFragment =
        SettingFragment()

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
        Toast.makeText(this@MainActivity, "${username}님 반갑습니다 :)", Toast.LENGTH_LONG).show()

        setFragment()
        setBottomNavigation()

        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, DiaryListInjector(
                this.application
            ).provideDiaryListViewModelFactory())
                .get(ListViewModel::class.java)
        }

        emotionViewModel = application!!.let {
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel::class.java)
        }

    }

    private fun setFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.contentFrame, listFragment).commit()

//        fragmentManager.beginTransaction().add(R.id.contentFrame, graphFragment).commit()
//        fragmentManager.beginTransaction().hide(graphFragment).commit()
//
//        fragmentManager.beginTransaction().add(R.id.contentFrame, settingFragment).commit()
//        fragmentManager.beginTransaction().hide(settingFragment).commit()
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
                    R.id.searchButton -> {
                        switchFragement(FRAG_GRAPH)
                        true
                    }
                    R.id.addButton -> {
                        switchFragement(FRAG_SETTING)
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
                fragmentManager.beginTransaction().replace(R.id.contentFrame, listFragment).commit()
            }
            FRAG_GRAPH -> {
                fragmentManager.beginTransaction().replace(R.id.contentFrame, graphFragment).commit()
            }
            FRAG_SETTING -> {
                fragmentManager.beginTransaction().replace(R.id.contentFrame, settingFragment).commit()
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
            R.id.nav_username_edit-> {
                val intent = Intent(applicationContext, UsernameChangeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_password_edit-> {
                val intent = Intent(applicationContext, PasswordChangeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout-> {
                val httpCall: ApiService?
                        = RetrofitClient.getClient(Constants.API_BASE_URL)!!.create(ApiService::class.java)
                httpCall?.logout(token)?.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d(Constants.TAG,"logout - onFailed() called / t: ${t}")
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response!!.code()) {
                            200 -> {
                                Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                            }
                            400 -> Toast.makeText(applicationContext, "로그아웃 실패 : ${response.message()}", Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }
        }
        return false
    }

}
