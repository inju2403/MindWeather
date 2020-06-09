package com.example.ttogilgi.diary.diaryList

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.ttogilgi.graph.EmotionViewModel
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.utils.Constants.TAG
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var listAdapter: DiaryListAdapter
    private var viewModel: ListViewModel? = null
    private var emotionViewModel: EmotionViewModel? = null

    private var happinessCnt = 0
    private var neutralityCnt = 0
    private var sadnessCnt = 0
    private var worryCnt = 0
    private var angerCnt = 0


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
            ViewModelProvider(viewModelStore, DiaryListInjector(
                requireActivity().application
            ).provideDiaryListViewModelFactory())
                .get(ListViewModel::class.java)
        }

        emotionViewModel = activity!!.application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel::class.java)
        }

        setUpAdapter()
        observeViewModel()
        observeEmotionViewModel()
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
        viewModel!!.getDiarys()
        Log.d(TAG,"리스트: ${viewModel!!.diaryListLiveData.value}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        diaryListView.adapter = null
    }


    private fun setUpAdapter() {
        listAdapter = DiaryListAdapter()
        listAdapter.event.observe(
            viewLifecycleOwner,
            Observer {
                viewModel!!.handleEvent(it)
            }
        )
    }

    private fun observeViewModel() {
        viewModel!!.let {
            it.diaryListLiveData.value?.let {
                diaryListView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                diaryListView.adapter = listAdapter
                var list = it as List<Diary>
                for(i in list.indices) {
                    happinessCnt += list[i].happiness
                    angerCnt += list[i].anger
                    worryCnt += list[i].worry
                    neutralityCnt += list[i].neutrality
                    sadnessCnt += list[i].sadness
                }
            }
            it.editDiary.observe(
                viewLifecycleOwner,
                Observer {
                    val intent = Intent(activity, DetailActivity::class.java).apply {
                        putExtra("DIARY_ID", it)
                        Log.d(TAG,"observer diary id: ${it}")
                    }
                    startActivity(intent)
                }
            )
            it.diaryListLiveData.observe(viewLifecycleOwner,
                Observer {
                    listAdapter.submitList(it)
                })
        }
    }

    private fun observeEmotionViewModel() {
        emotionViewModel!!.let {
            it.emotionLiveData.value?.let {
                it.happiness = happinessCnt
                it.anger = angerCnt
                it.neutrality = neutralityCnt
                it.sadness = sadnessCnt
                it.worry = worryCnt
            }
        }
    }
}
