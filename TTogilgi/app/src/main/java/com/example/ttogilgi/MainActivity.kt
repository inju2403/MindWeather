package com.example.ttogilgi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.diary.ListViewModel
import com.example.ttogilgi.diary.diaryList.ListFragment
import com.example.ttogilgi.diary.diaryList.buildlogic.DiaryListInjector
import com.example.ttogilgi.graph.EmotionViewModel
import com.example.ttogilgi.graph.GraphFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PREFERENCE = "template.android.TTogilgi"

    private var viewModel: ListViewModel? = null
    private var emotionViewModel: EmotionViewModel? = null

    private val FRAG_LIST = 0
    private val FRAG_GRAPH = 1
    private val FRAG_SETTING = 2

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

        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val token = "JWT ${pref.getString("token", "").toString()}"
        val username = pref.getString("username", "").toString()
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
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
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

}
