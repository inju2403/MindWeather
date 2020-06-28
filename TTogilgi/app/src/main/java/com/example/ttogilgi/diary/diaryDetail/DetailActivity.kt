package com.example.ttogilgi.diary.diaryDetail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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


class DetailActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var runnable: Runnable? =null

    private lateinit var viewModel: DetailViewModel

    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            runnable = Runnable {
                progressBar.visibility = View.GONE
                progressBarText.visibility = View.GONE

                if(viewModel.diary.happiness==1) {
                    emotionImage.setImageResource(R.drawable.ic_happiness)
                    emotionText.text = "행복했던 하루"
                }
                else if(viewModel.diary.worry==1) {
                    emotionImage.setImageResource(R.drawable.ic_worry)
                    emotionText.text = "걱정되었던 하루"
                }
                else if(viewModel.diary.anger==1) {
                    emotionImage.setImageResource(R.drawable.ic_anger)
                    emotionText.text = "화났던 하루"
                }
                else if(viewModel.diary.sadness==1) {
                    emotionImage.setImageResource(R.drawable.ic_sadness)
                    emotionText.text = "슬펐던 하루"
                }
                else if(viewModel.diary.neutrality==1) {
                    emotionImage.setImageResource(R.drawable.ic_neatrality)
                    emotionText.text = "무난했던 하루"
                }
                else {
                    emotionImage.setImageResource(R.drawable.ic_unknowability)
                    emotionText.text = "복합적인 감정의 하루"
                }
                emotionImage.visibility = View.VISIBLE
                emotionText.visibility = View.VISIBLE
                contentView.visibility = View.VISIBLE
            }
            handler = Handler()
            handler?.run {
                viewModel!!.loadDiary(context, diaryId)
                progressBar.visibility = View.VISIBLE
                progressBarText.visibility = View.VISIBLE
                postDelayed(runnable, 2000)
            }

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

}
