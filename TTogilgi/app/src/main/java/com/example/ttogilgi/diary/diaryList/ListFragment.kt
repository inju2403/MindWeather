package com.example.ttogilgi.diary.diaryList

import android.app.Activity.RESULT_OK
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
import com.example.ttogilgi.graph.EmotionViewModel1month
import com.example.ttogilgi.graph.EmotionViewModel1week
import com.example.ttogilgi.graph.EmotionViewModel1year
import com.example.ttogilgi.graph.EmotionViewModel6month
import com.example.ttogilgi.model.Emotion
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.utils.Constants.TAG
import kotlinx.android.synthetic.main.fragment_list.*
import java.text.SimpleDateFormat
import java.util.*

class ListFragment : Fragment() {

    private var curTimeValue: Int = 0
    private val yearDateFormat = SimpleDateFormat("YYYY")
    private val monthDateFormat = SimpleDateFormat("MM")
    private val dayDateFormat = SimpleDateFormat("dd")

    private val REQUEST_EDITACTIVITY_CODE = 101

    private lateinit var listAdapter: DiaryListAdapter
    private var viewModel: ListViewModel? = null
    private var emotionViewModel_1week: EmotionViewModel1week? = null
    private var emotionViewModel_1month: EmotionViewModel1month? = null
    private var emotionViewModel_6month: EmotionViewModel6month? = null
    private var emotionViewModel_1year: EmotionViewModel1year? = null


    private var happinessCnt1year = 0.0
    private var happinessCnt6month = 0.0
    private var happinessCnt1month = 0.0
    private var happinessCnt1week = 0.0

    private var neutralityCnt1year = 0.0
    private var neutralityCnt6month = 0.0
    private var neutralityCnt1month = 0.0
    private var neutralityCnt1week = 0.0

    private var sadnessCnt1year = 0.0
    private var sadnessCnt6month = 0.0
    private var sadnessCnt1month = 0.0
    private var sadnessCnt1week = 0.0

    private var worryCnt1year = 0.0
    private var worryCnt6month = 0.0
    private var worryCnt1month = 0.0
    private var worryCnt1week = 0.0

    private var angerCnt1year = 0.0
    private var angerCnt6month = 0.0
    private var angerCnt1month = 0.0
    private var angerCnt1week = 0.0


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

        emotionViewModel_1week = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel1week::class.java)
        }
        emotionViewModel_1month = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel1month::class.java)
        }
        emotionViewModel_6month = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel6month::class.java)
        }
        emotionViewModel_1year = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel1year::class.java)
        }

        var curYear = yearDateFormat.format(Date()).toInt()
        var curMonth = + monthDateFormat.format(Date()).toInt()
        var curDay = dayDateFormat.format(Date()).toInt()

        curTimeValue = curYear * (12 * 30) + curMonth * 30 + curDay

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
                startActivityForResult(intent, REQUEST_EDITACTIVITY_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.getDiarys()
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
                    happinessCnt1year = 0.0
                    happinessCnt6month = 0.0
                    happinessCnt1month = 0.0
                    happinessCnt1week = 0.0

                    neutralityCnt1year = 0.0
                    neutralityCnt6month = 0.0
                    neutralityCnt1month = 0.0
                    neutralityCnt1week = 0.0

                    sadnessCnt1year = 0.0
                    sadnessCnt6month = 0.0
                    sadnessCnt1month = 0.0
                    sadnessCnt1week = 0.0

                    worryCnt1year = 0.0
                    worryCnt6month = 0.0
                    worryCnt1month = 0.0
                    worryCnt1week = 0.0

                    angerCnt1year = 0.0
                    angerCnt6month = 0.0
                    angerCnt1month = 0.0
                    angerCnt1week = 0.0

                    for(i in list.indices) {
                        var diaryYear = yearDateFormat.format(list[i].createdAt).toInt()
                        var diaryMonth = + monthDateFormat.format(list[i].createdAt).toInt()
                        var diaryDay = dayDateFormat.format(list[i].createdAt).toInt()

                        var diaryTimeValue = diaryYear * (12 * 30) + diaryMonth * 30 + diaryDay

//                        Log.d(TAG,"날짜 차이 ${curTimeValue-diaryTimeValue}")

                        if(curTimeValue - diaryTimeValue <= 7) { // 한 주
                            if(list[i].happiness > 0) happinessCnt1week += 1.0
                            if(list[i].anger > 0) angerCnt1week += 1.0
                            if(list[i].worry >0) worryCnt1week += 1.0
                            if(list[i].sadness > 0) sadnessCnt1week += 1.0
                            if(list[i].neutrality > 0) neutralityCnt1week += 1.0
                        }

                        if(curTimeValue - diaryTimeValue <= 30) { // 한 달
                            if(list[i].happiness > 0) happinessCnt1month += 1.0
                            if(list[i].anger > 0) angerCnt1month += 1.0
                            if(list[i].worry >0) worryCnt1month += 1.0
                            if(list[i].sadness > 0) sadnessCnt1month += 1.0
                            if(list[i].neutrality > 0) neutralityCnt1month += 1.0
                        }

                        if(curTimeValue - diaryTimeValue <= 180) { // 6개월
                            if(list[i].happiness > 0) happinessCnt6month += 1.0
                            if(list[i].anger > 0) angerCnt6month += 1.0
                            if(list[i].worry >0) worryCnt6month += 1.0
                            if(list[i].sadness > 0) sadnessCnt6month += 1.0
                            if(list[i].neutrality > 0) neutralityCnt6month += 1.0
                        }

                        if(curTimeValue - diaryTimeValue <= 365) { // 1년
                            if(list[i].happiness > 0) happinessCnt1year += 1.0
                            if(list[i].anger > 0) angerCnt1year += 1.0
                            if(list[i].worry >0) worryCnt1year += 1.0
                            if(list[i].sadness > 0) sadnessCnt1year += 1.0
                            if(list[i].neutrality > 0) neutralityCnt1year += 1.0
                        }
                    }

                    val newEmotion1week = Emotion(happinessCnt1week, neutralityCnt1week, sadnessCnt1week, worryCnt1week, angerCnt1week)
                    emotionViewModel_1week!!.setEmotions(newEmotion1week)

                    val newEmotion1month = Emotion(happinessCnt1month, neutralityCnt1month, sadnessCnt1month, worryCnt1month, angerCnt1month)
                    emotionViewModel_1month!!.setEmotions(newEmotion1month)

                    val newEmotion6month = Emotion(happinessCnt6month, neutralityCnt6month, sadnessCnt6month, worryCnt6month, angerCnt6month)
                    emotionViewModel_6month!!.setEmotions(newEmotion6month)

                    val newEmotion1year = Emotion(happinessCnt1year, neutralityCnt1year, sadnessCnt1year, worryCnt1year, angerCnt1year)
                    emotionViewModel_1year!!.setEmotions(newEmotion1year)
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_EDITACTIVITY_CODE) {
            Log.d(TAG,"req ok")
            if(resultCode == RESULT_OK) {
                Log.d(TAG,"res ok")
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
}
