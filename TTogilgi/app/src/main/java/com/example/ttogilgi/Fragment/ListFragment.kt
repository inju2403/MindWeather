package com.example.ttogilgi.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ttogilgi.Data.DiaryData
import com.example.ttogilgi.Data.DiaryListAdapter
import com.example.ttogilgi.Data.ListViewModel
import com.example.ttogilgi.R
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class ListFragment : Fragment() {

    private lateinit var listAdapter: DiaryListAdapter
    private var viewModel: ListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java)
        }

        viewModel!!.let {
            it.diaryLiveData.value?.let {
                listAdapter = DiaryListAdapter(it)
                diaryListView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                diaryListView.adapter = listAdapter
            }
            it.diaryLiveData.observe(this,
                Observer {
                    listAdapter.notifyDataSetChanged()
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        activity!!.menuInflater.inflate(R.menu.list_fragment_toolbar_menu, menu)
    }

    @SuppressLint("MissingPermission")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.searchButton -> {

            }
            R.id.addButton -> {
                // viewModel?.addOrUpdateDiary(this)
                viewModel!!.let {
                    var diaryData = DiaryData()
                    diaryData.summary = "오늘은 냉면을 먹었다. 너무 시원했다 ㅎㅎ 역시 냉면은 물냉이랑 비냉을 같이 시켜야 해!!"
                    diaryData.createdAt = Date()

                    it.addDiary(diaryData)
                }
                Toast.makeText(context,
                    "저장 완료", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
