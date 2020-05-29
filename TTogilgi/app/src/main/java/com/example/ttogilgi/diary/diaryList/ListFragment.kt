package com.example.ttogilgi.diary.diaryList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ttogilgi.R
import com.example.ttogilgi.diary.ListViewModel
import com.example.ttogilgi.diary.diaryDetail.DetailActivity
import com.example.ttogilgi.diary.diaryDetail.EditActivity
import com.example.ttogilgi.diary.diaryList.buildlogic.DiaryListInjector
import kotlinx.android.synthetic.main.fragment_list.*

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

        viewModel = activity!!.application!!.let {
            ViewModelProvider(this, DiaryListInjector(
                requireActivity().application
            ).provideDiaryListViewModelFactory())
                .get(ListViewModel::class.java)
        }

        viewModel!!.let {
            it.diaryLiveData.value?.let {
                listAdapter =
                    DiaryListAdapter(it)
                diaryListView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                diaryListView.adapter = listAdapter
                listAdapter.itemClickListener = {
                    val intent = Intent(activity, DetailActivity::class.java).apply {
                        putExtra("DIARY_ID", it)
                    }
                    startActivity(intent)
                }
            }
            it.diaryLiveData.observe(viewLifecycleOwner,
                Observer {
                    listAdapter.notifyDataSetChanged()
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        activity!!.menuInflater.inflate(R.menu.list_fragment_toolbar_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.searchButton -> {

            }
            R.id.addButton -> {
                val intent = Intent(activity!!.applicationContext, EditActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        listAdapter.notifyDataSetChanged()
    }
}
