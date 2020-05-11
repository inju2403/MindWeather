package com.example.ttogilgi.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.example.ttogilgi.data.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)
        supportActionBar!!.title = ""

        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(DetailViewModel::class.java)
        }

        viewModel!!.diaryLiveData.observe (this, Observer {
            contentView.text = it.content
        })

        val diaryId = intent.getStringExtra("DIARY_ID")
        if(diaryId != null) viewModel!!.loadDiary(diaryId)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_activity_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_modify -> {
                val tag = intent.getStringExtra("DIARY_ID")
                val intent = Intent(applicationContext, EditActivity::class.java).apply {
                    putExtra("DIARY_ID", tag)
                }
                startActivity(intent)
                finish()
            }
            R.id.action_delete -> {
                val diaryId = intent.getStringExtra("DIARY_ID")
                viewModel?.deleteDiary(diaryId)
                Toast.makeText(this,
                    "삭제 완료", Toast.LENGTH_LONG).show()
                finish()
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
