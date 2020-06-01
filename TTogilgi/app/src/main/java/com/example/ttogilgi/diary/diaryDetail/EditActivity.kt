package com.example.ttogilgi.diary.diaryDetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.example.ttogilgi.diary.DetailViewModel
import com.example.ttogilgi.diary.diaryDetail.buildlogic.DiaryDetailInjector
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(editToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        viewModel = application!!.let {
            ViewModelProvider(this, DiaryDetailInjector(
                this.application
            ).provideDiaryViewModelFactory())
                .get(DetailViewModel::class.java)
        }

        viewModel!!.diaryLiveData.observe (this, Observer {
            contentEdit.setText(it.content)
        })

        val diaryId = intent.getStringExtra("DIARY_ID")
        if(diaryId != null) viewModel!!.loadDiary(this, diaryId)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_activity_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_save -> {
                viewModel?.addOrUpdateDiary(this)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
