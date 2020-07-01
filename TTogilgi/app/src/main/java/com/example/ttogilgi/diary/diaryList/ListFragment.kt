package com.example.ttogilgi.diary.diaryList

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.example.ttogilgi.model.Emotion
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.utils.Constants.TAG
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private val RQ_CODE = 101

    private lateinit var listAdapter: DiaryListAdapter
    private var viewModel: ListViewModel? = null
    private var emotionViewModel: EmotionViewModel? = null

    private var happinessCnt = 0.0
    private var neutralityCnt = 0.0
    private var sadnessCnt = 0.0
    private var worryCnt = 0.0
    private var angerCnt = 0.0

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
            ViewModelProvider(activity!!.viewModelStore, DiaryListInjector(
                requireActivity().application
            ).provideDiaryListViewModelFactory())
                .get(ListViewModel::class.java)
        }

        emotionViewModel = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel::class.java)
        }

        setUpAdapter()
        observeViewModel()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        activity!!.menuInflater.inflate(R.menu.list_fragment_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.addButton -> {
                val intent = Intent(activity!!.applicationContext, EditActivity::class.java)
//                startActivity(intent)
                startActivityForResult(intent, RQ_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.getDiarys()
//        Log.d(TAG,"(뷰모델) 리스트: ${viewModel!!.diaryListLiveData.value}")

//        // recyclerview scroll to top
//        val handler = Handler()
//        val runnable = Runnable {
//            diaryListView.smoothScrollToPosition(0)
//        }
//        handler?.run {
//            postDelayed(runnable, 100)
//        }
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
//                diaryListView.smoothScrollToPosition(0)
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

                    // observer emotion
                    var list = it as List<Diary>
                    happinessCnt = 0.0
                    neutralityCnt = 0.0
                    sadnessCnt = 0.0
                    worryCnt = 0.0
                    angerCnt = 0.0
                    for(i in list.indices) {

                        var emotionValues = arrayListOf(list[i].happiness,
                            list[i].sadness, list[i].worry, list[i].anger, list[i].neutrality)
                        var sortedEmotionValues = emotionValues.sortedDescending()

                        if(sortedEmotionValues[0] == sortedEmotionValues[1]) {
                            //복합 감정 (점유율이 가장 높은 감정이 2개인 경우)
                            var sum = list[i].happiness + list[i].anger + list[i].worry + list[i].neutrality + list[i].sadness

                            happinessCnt += list[i].happiness.toDouble() / sum.toDouble()
                            angerCnt += list[i].anger.toDouble() / sum.toDouble()
                            worryCnt += list[i].worry.toDouble() / sum.toDouble()
                            neutralityCnt += list[i].neutrality.toDouble() / sum.toDouble()
                            sadnessCnt += list[i].sadness.toDouble() / sum.toDouble()
                        }

                        else {
                            // 점유율이 가장 높은 감정이 1개인 경우
                            if(sortedEmotionValues[0] == list[i].happiness) {
                                happinessCnt += 1.0
                            }
                            else if(sortedEmotionValues[0] == list[i].sadness) {
                                angerCnt += 1.0
                            }
                            else if(sortedEmotionValues[0] == list[i].worry) {
                                worryCnt += 1.0
                            }
                            else if(sortedEmotionValues[0] == list[i].anger) {
                                neutralityCnt += 1.0
                            }
                            else if(sortedEmotionValues[0] == list[i].neutrality) {
                                sadnessCnt += 1.0
                            }
                        }
                    }
                    val newEmotion: Emotion = Emotion(happinessCnt, neutralityCnt, sadnessCnt, worryCnt, angerCnt)
                    emotionViewModel!!.setEmotions(newEmotion)
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RQ_CODE) {
            val handler = Handler()
            val runnable = Runnable {
                diaryListView.smoothScrollToPosition(0)
            }
            handler?.run {
                postDelayed(runnable, 1000)
            }
        }
    }
}
