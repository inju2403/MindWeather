package com.example.ttogilgi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.example.ttogilgi.data.ListViewModel
import com.example.ttogilgi.fragment.GraphFragment
import com.example.ttogilgi.fragment.ListFragment
import com.example.ttogilgi.fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PREFERENCE = "template.android.TTogilgi"

    private var viewModel: ListViewModel? = null

    private val FRAG_LIST = 0
    private val FRAG_GRAPH = 1
    private val FRAG_SETTING = 2

    private val listFragment: ListFragment = ListFragment()
    private val graphFragment: GraphFragment = GraphFragment()
    private val settingFragment: SettingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainActivityToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        var pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        var username = pref.getString("username", "")

        setFragment()
        setBottomNavigation()

        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java)
        }
    }

    private fun setFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.contentFrame, listFragment).commit()

        fragmentManager.beginTransaction().add(R.id.contentFrame, graphFragment).commit()
        fragmentManager.beginTransaction().hide(graphFragment).commit()

        fragmentManager.beginTransaction().add(R.id.contentFrame, settingFragment).commit()
        fragmentManager.beginTransaction().hide(settingFragment).commit()
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
                fragmentManager.beginTransaction().show(listFragment).commit()
                fragmentManager.beginTransaction().hide(graphFragment).commit()
                fragmentManager.beginTransaction().hide(settingFragment).commit()
            }
            FRAG_GRAPH -> {
                fragmentManager.beginTransaction().hide(listFragment).commit()
                fragmentManager.beginTransaction().show(graphFragment).commit()
                fragmentManager.beginTransaction().hide(settingFragment).commit()
            }
            FRAG_SETTING -> {
                fragmentManager.beginTransaction().hide(listFragment).commit()
                fragmentManager.beginTransaction().hide(graphFragment).commit()
                fragmentManager.beginTransaction().show(settingFragment).commit()
            }
        }
    }

}
