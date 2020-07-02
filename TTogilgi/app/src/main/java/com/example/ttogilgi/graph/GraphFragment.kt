package com.example.ttogilgi.graph

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.example.ttogilgi.utils.Constants.PREFERENCE
import com.example.ttogilgi.utils.Constants.TAG
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : Fragment() {

    private var emotionViewModel_1week: EmotionViewModel1week? = null
    private var emotionViewModel_1month: EmotionViewModel1month? = null
    private var emotionViewModel_6month: EmotionViewModel6month? = null
    private var emotionViewModel_1year: EmotionViewModel1year? = null

    lateinit var username: String

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
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pref = activity!!.getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        username = pref.getString("username", "").toString()

        setEmotionViewModel()
        setTabLayout()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        graphTabLayout.getTabAt(0)?.select()
        makeChart1year()
    }

    private fun setTabLayout() {
        graphTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                Log.d(TAG,"selected: ${p0?.position}")
                when(p0?.position) {
                    0 -> makeChart1year() // 1년
                    1 -> makeChart6month() // 6개월
                    2 -> makeChart1month() // 한 달
                    3 -> makeChart1week() // 한 주
                }
            }

        })
    }

    private fun setEmotionViewModel() {
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



        emotionViewModel_1week!!.emotionLiveData.observe(viewLifecycleOwner, Observer {
            happinessCnt1week = it.happiness
            neutralityCnt1week = it.neutrality
            sadnessCnt1week = it.sadness
            worryCnt1week = it.worry
            angerCnt1week = it.anger
        })

        emotionViewModel_1month!!.emotionLiveData.observe(viewLifecycleOwner, Observer {
            happinessCnt1month = it.happiness
            neutralityCnt1month = it.neutrality
            sadnessCnt1month = it.sadness
            worryCnt1month = it.worry
            angerCnt1month = it.anger
        })

        emotionViewModel_6month!!.emotionLiveData.observe(viewLifecycleOwner, Observer {
            happinessCnt6month = it.happiness
            neutralityCnt6month = it.neutrality
            sadnessCnt6month = it.sadness
            worryCnt6month = it.worry
            angerCnt6month = it.anger
        })

        emotionViewModel_1year!!.emotionLiveData.observe(viewLifecycleOwner, Observer {
            happinessCnt1year = it.happiness
            neutralityCnt1year = it.neutrality
            sadnessCnt1year = it.sadness
            worryCnt1year = it.worry
            angerCnt1year = it.anger
        })
    }

    private fun makeChart1week() {
        emotion_graph.setUsePercentValues(true)
        emotion_graph.description.isEnabled = false
        emotion_graph.setExtraOffsets(5F, 0F, 5F, 10F)

        emotion_graph.dragDecelerationFrictionCoef = 0.95f

        emotion_graph.isDrawHoleEnabled = true
        emotion_graph.setHoleColor(1)
        emotion_graph.transparentCircleRadius = 61f

        emotion_graph.setDrawCenterText(true)
        emotion_graph.centerText = "나의 감정 분포"
        emotion_graph.setCenterTextColor(Color.DKGRAY)
        emotion_graph.setCenterTextSize(18f)

        val yValues = ArrayList<PieEntry>()

        if(happinessCnt1week>0) yValues.add(PieEntry(happinessCnt1week.toFloat(), "행복"))
        if(neutralityCnt1week>0) yValues.add(PieEntry(neutralityCnt1week.toFloat(), "중립"))
        if(worryCnt1week>0) yValues.add(PieEntry(worryCnt1week.toFloat(), "걱정"))
        if(sadnessCnt1week>0) yValues.add(PieEntry(sadnessCnt1week.toFloat(), "슬픔"))
        if(angerCnt1week>0) yValues.add(PieEntry(angerCnt1week.toFloat(), "분노"))


        emotion_graph.animateY(1000, Easing.EaseInOutCubic) //애니메이션

        val dataSet = PieDataSet(yValues,"")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)

        emotion_graph.data = data
    }

    private fun makeChart1month() {
        emotion_graph.setUsePercentValues(true)
        emotion_graph.description.isEnabled = false
        emotion_graph.setExtraOffsets(5F, 0F, 5F, 10F)

        emotion_graph.dragDecelerationFrictionCoef = 0.95f

        emotion_graph.isDrawHoleEnabled = true
        emotion_graph.setHoleColor(1)
        emotion_graph.transparentCircleRadius = 61f

        emotion_graph.setDrawCenterText(true)
        emotion_graph.centerText = "나의 감정 분포"
        emotion_graph.setCenterTextColor(Color.DKGRAY)
        emotion_graph.setCenterTextSize(18f)

        val yValues = ArrayList<PieEntry>()

        if(happinessCnt1month>0) yValues.add(PieEntry(happinessCnt1month.toFloat(), "행복"))
        if(neutralityCnt1month>0) yValues.add(PieEntry(neutralityCnt1month.toFloat(), "중립"))
        if(worryCnt1month>0) yValues.add(PieEntry(worryCnt1month.toFloat(), "걱정"))
        if(sadnessCnt1month>0) yValues.add(PieEntry(sadnessCnt1month.toFloat(), "슬픔"))
        if(angerCnt1month>0) yValues.add(PieEntry(angerCnt1month.toFloat(), "분노"))


        emotion_graph.animateY(1000, Easing.EaseInOutCubic) //애니메이션

        val dataSet = PieDataSet(yValues,"")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)

        emotion_graph.data = data
    }

    private fun makeChart6month() {
        emotion_graph.setUsePercentValues(true)
        emotion_graph.description.isEnabled = false
        emotion_graph.setExtraOffsets(5F, 0F, 5F, 10F)

        emotion_graph.dragDecelerationFrictionCoef = 0.95f

        emotion_graph.isDrawHoleEnabled = true
        emotion_graph.setHoleColor(1)
        emotion_graph.transparentCircleRadius = 61f

        emotion_graph.setDrawCenterText(true)
        emotion_graph.centerText = "나의 감정 분포"
        emotion_graph.setCenterTextColor(Color.DKGRAY)
        emotion_graph.setCenterTextSize(18f)

        val yValues = ArrayList<PieEntry>()

        if(happinessCnt6month>0) yValues.add(PieEntry(happinessCnt6month.toFloat(), "행복"))
        if(neutralityCnt6month>0) yValues.add(PieEntry(neutralityCnt6month.toFloat(), "중립"))
        if(worryCnt6month>0) yValues.add(PieEntry(worryCnt6month.toFloat(), "걱정"))
        if(sadnessCnt6month>0) yValues.add(PieEntry(sadnessCnt6month.toFloat(), "슬픔"))
        if(angerCnt6month>0) yValues.add(PieEntry(angerCnt6month.toFloat(), "분노"))


        emotion_graph.animateY(1000, Easing.EaseInOutCubic) //애니메이션

        val dataSet = PieDataSet(yValues,"")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)

        emotion_graph.data = data
    }

    private fun makeChart1year() {
        emotion_graph.setUsePercentValues(true)
        emotion_graph.description.isEnabled = false
        emotion_graph.setExtraOffsets(5F, 0F, 5F, 10F)

        emotion_graph.dragDecelerationFrictionCoef = 0.95f

        emotion_graph.isDrawHoleEnabled = true
        emotion_graph.setHoleColor(1)
        emotion_graph.transparentCircleRadius = 61f

        emotion_graph.setDrawCenterText(true)
        emotion_graph.centerText = "나의 감정 분포"
        emotion_graph.setCenterTextColor(Color.DKGRAY)
        emotion_graph.setCenterTextSize(18f)

        val yValues = ArrayList<PieEntry>()

        if(happinessCnt1year>0) yValues.add(PieEntry(happinessCnt1year.toFloat(), "행복"))
        if(neutralityCnt1year>0) yValues.add(PieEntry(neutralityCnt1year.toFloat(), "중립"))
        if(worryCnt1year>0) yValues.add(PieEntry(worryCnt1year.toFloat(), "걱정"))
        if(sadnessCnt1year>0) yValues.add(PieEntry(sadnessCnt1year.toFloat(), "슬픔"))
        if(angerCnt1year>0) yValues.add(PieEntry(angerCnt1year.toFloat(), "분노"))


        emotion_graph.animateY(1000, Easing.EaseInOutCubic) //애니메이션

        val dataSet = PieDataSet(yValues,"")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)

        emotion_graph.data = data
    }

}
