package com.example.ttogilgi.diary.diaryDetail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.example.ttogilgi.diary.DetailViewModel
import com.example.ttogilgi.diary.diaryDetail.buildlogic.DiaryDetailInjector
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class DetailActivity : AppCompatActivity(), CoroutineScope {

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
                val view = LayoutInflater.from(this).inflate(R.layout.dialog_delete, null)
                val builder = AlertDialog.Builder(this, R.style.DialogTheme)

                val dialog =
                    builder
                        .setTitle("일기를 삭제하시겠어요?")
                        .setView(view)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            val diaryId = intent.getStringExtra("DIARY_ID")
                            viewModel?.deleteDiary(this, diaryId)
                            Toast.makeText(this,
                                "삭제 완료", Toast.LENGTH_LONG).show()
                            finish()
                        }).create()
                dialog.show()
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

        if (viewModel.diary.happiness == 1) {
            emotionImage.setImageResource(R.drawable.ic_happiness)
            emotionText.text = "행복했던 하루"
        } else if (viewModel.diary.worry == 1) {
            emotionImage.setImageResource(R.drawable.ic_worry)
            emotionText.text = "걱정되었던 하루"
        } else if (viewModel.diary.anger == 1) {
            emotionImage.setImageResource(R.drawable.ic_anger)
            emotionText.text = "화났던 하루"
        } else if (viewModel.diary.sadness == 1) {
            emotionImage.setImageResource(R.drawable.ic_sadness)
            emotionText.text = "슬펐던 하루"
        } else if (viewModel.diary.neutrality == 1) {
            emotionImage.setImageResource(R.drawable.ic_neatrality)
            emotionText.text = "무난했던 하루"
        } else {
            emotionImage.setImageResource(R.drawable.ic_unknowability)
            emotionText.text = "복합적인 감정의 하루"
        }
        emotionImage.visibility = View.VISIBLE
        emotionText.visibility = View.VISIBLE
        contentView.visibility = View.VISIBLE
    }
}
