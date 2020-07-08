package com.example.ttogilgi.diary.diaryDetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.example.ttogilgi.diary.DetailViewModel
import com.example.ttogilgi.diary.diaryDetail.buildlogic.DiaryDetailInjector
import com.example.ttogilgi.utils.MyDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class DetailActivity : AppCompatActivity(), CoroutineScope {

    private val RETURN_OK = 101

    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var viewModel: DetailViewModel

    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        progressBar.visibility = View.GONE
        progressBarText.visibility = View.GONE

        emotionImage.visibility = View.GONE
        emotionText.visibility = View.GONE
        contentView.visibility = View.GONE

        viewModel = application!!.let {
            ViewModelProvider(this, DiaryDetailInjector(
                this.application
            ).provideDiaryViewModelFactory())
                .get(DetailViewModel::class.java)
        }

        viewModel!!.diaryLiveData.observe (this, Observer {
            contentView.text = it.content
        })

        val diaryId = intent.getStringExtra("DIARY_ID")
        if(diaryId != null) {
            diaryLoading(diaryId)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_activity_toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_modify -> {
                val tag = intent.getStringExtra("DIARY_ID")
                val intent = Intent(applicationContext, EditActivity::class.java).apply {
                    putExtra("DIARY_ID", tag)
                }
                startActivity(intent)
                finish()
            }
            R.id.action_delete -> {
                val dialog = MyDialog(this)
                dialog.start("일기를 삭제하시겠어요?")
                dialog.setOnOKClickedListener {
                    if(it == RETURN_OK) {
                        val diaryId = intent.getStringExtra("DIARY_ID")
                        viewModel?.deleteDiary(this, diaryId)
                        Toast.makeText(this,
                            "삭제 완료", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
            R.id.menu_share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, supportActionBar?.title)
                intent.putExtra(Intent.EXTRA_TEXT, contentView.text.toString())

                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun diaryLoading(diaryId: String) = launch {

        progressBar.visibility = View.VISIBLE
        progressBarText.visibility = View.VISIBLE

        viewModel!!.loadDiary(context, diaryId).join()

        progressBar.visibility = View.GONE
        progressBarText.visibility = View.GONE

        //감정 표정 세팅
        var emotionValues = arrayListOf(
            viewModel.diary.happiness, viewModel.diary.sadness,
            viewModel.diary.worry, viewModel.diary.anger, viewModel.diary.neutrality)

        var sortedEmotionValues = emotionValues.sortedDescending()

        if(sortedEmotionValues[0] == sortedEmotionValues[1]) {
            //복합 감정
            emotionImage.setImageResource(R.drawable.ic_unknowability)
            emotionText.text = "복합적인 감정의 하루"
        }
        else if(sortedEmotionValues[0] == viewModel.diary.happiness) {
            emotionImage.setImageResource(R.drawable.ic_happiness)
            emotionText.text = "행복을 느낀 하루"
        }
        else if(sortedEmotionValues[0] == viewModel.diary.sadness) {
            emotionImage.setImageResource(R.drawable.ic_sadness)
            emotionText.text = "슬픔을 느낀 하루"
        }
        else if(sortedEmotionValues[0] == viewModel.diary.worry) {
            emotionImage.setImageResource(R.drawable.ic_worry)
            emotionText.text = "걱정을 느낀 하루"
        }
        else if(sortedEmotionValues[0] == viewModel.diary.anger) {
            emotionImage.setImageResource(R.drawable.ic_anger)
            emotionText.text = "화나는 감정을 느낀 하루"
        }
        else if(sortedEmotionValues[0] == viewModel.diary.neutrality) {
            emotionImage.setImageResource(R.drawable.ic_neutrality)
            emotionText.text = "어느 한쪽으로 치우치지 않은 감정의 하루"
        }

        emotionImage.visibility = View.VISIBLE
        emotionText.visibility = View.VISIBLE
        contentView.visibility = View.VISIBLE
    }
}
