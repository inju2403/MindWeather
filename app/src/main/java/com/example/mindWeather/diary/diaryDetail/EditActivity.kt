package com.example.mindWeather.diary.diaryDetail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mindWeather.R
import com.example.mindWeather.diary.DetailViewModel
import com.example.mindWeather.diary.diaryDetail.buildlogic.DiaryDetailInjector
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class EditActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var viewModel: DetailViewModel? = null
    private var context = this

    private val dateFormat = SimpleDateFormat("MMdd")
    private val weekdayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        setContentView(R.layout.activity_edit)
        setSupportActionBar(editToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        progressBar.visibility = View.GONE
        progressBarText.visibility = View.GONE

        viewModel = application!!.let {
            ViewModelProvider(this, DiaryDetailInjector(
                this.application
            ).provideDiaryViewModelFactory())
                .get(DetailViewModel::class.java)
        }

        viewModel!!.diaryLiveData.observe (this, Observer {
            contentEdit.setText(it.content)
        })

        contentEdit.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel!!.diary.content = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        val diaryId = intent.getStringExtra("DIARY_ID")
        if(diaryId != null) {
            diaryLoading(diaryId)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_activity_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                setResult(RESULT_CANCELED, intent)
                finish()
            }
            R.id.action_save -> {
                diaryUpdateLoading()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun diaryUpdateLoading() = launch {
        progressBar.visibility = View.VISIBLE
        progressBarText.text = "감정 분석 중입니다"
        progressBarText.visibility = View.VISIBLE

        viewModel?.addOrUpdateDiary(context)!!.join()

        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun diaryLoading(diaryId: String) = launch {

        progressBar.visibility = View.VISIBLE
        progressBarText.text = "일기를 불러오는 중입니다"
        progressBarText.visibility = View.VISIBLE

        titleText.visibility = View.GONE
        dateView.visibility = View.GONE
        dayOfTheWeekView.visibility = View.GONE

        viewModel!!.loadDiary(context, diaryId).join()

        progressBar.visibility = View.GONE
        progressBarText.visibility = View.GONE


        dateView.text = dateFormat.format(viewModel!!.diary.createdAt)
        dayOfTheWeekView.text = weekdayFormat.format(viewModel!!.diary.createdAt)

        titleText.visibility = View.VISIBLE
        dateView.visibility = View.VISIBLE
        dayOfTheWeekView.visibility = View.VISIBLE

    }
}
