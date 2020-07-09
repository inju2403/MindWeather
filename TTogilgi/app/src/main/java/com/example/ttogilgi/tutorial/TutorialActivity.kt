package com.example.ttogilgi.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ttogilgi.R
import com.example.ttogilgi.login.LoginActivity
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : FragmentActivity() {

    private var pageItemList = ArrayList<PageItem>()
    private lateinit var tutorialAdapter: TutorialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        pageItemList.add(PageItem(R.drawable.ic_tutorial_1))
        pageItemList.add(PageItem(R.drawable.ic_tutorial_2))
        pageItemList.add(PageItem(R.drawable.ic_tutorial_3))
        pageItemList.add(PageItem(R.drawable.ic_tutorial_4))
        pageItemList.add(PageItem(R.drawable.ic_tutorial_5))
        pageItemList.add(PageItem(R.drawable.ic_tutorial_6))
        pageItemList.add(PageItem(R.drawable.ic_tutorial_final))

        tutorialAdapter = TutorialAdapter(pageItemList)

        tutorial_view_pager.apply {
            adapter = tutorialAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            dots_indicator.setViewPager2(this)
        }

        tutorial_view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if(position == pageItemList.size - 1) {
                    next_button.text = "시작"
                } else {
                    next_button.text = "다음"
                }
            }
        })

        previous_button.setOnClickListener {
            tutorial_view_pager.currentItem = tutorial_view_pager.currentItem - 1
        }

        next_button.setOnClickListener {
            if(tutorial_view_pager.currentItem == pageItemList.size - 1) {
                startActivity(Intent(this@TutorialActivity, LoginActivity::class.java))
                finish()
            }
            tutorial_view_pager.currentItem = tutorial_view_pager.currentItem + 1
        }
    }

}
