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
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : Fragment() {

    private var emotionViewModel: EmotionViewModel? = null

    private var happinessCnt = 0
    private var neutralityCnt = 0
    private var sadnessCnt = 0
    private var worryCnt = 0
    private var angerCnt = 0
    lateinit var username: String

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

        emotionViewModel = activity!!.application!!.let {
            ViewModelProvider(activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel::class.java)
        }

        emotionViewModel!!.emotionLiveData.observe(viewLifecycleOwner, Observer {
            happinessCnt = it.happiness
            neutralityCnt = it.neutrality
            sadnessCnt = it.sadness
            worryCnt = it.worry
            angerCnt = it.anger
            Log.d(TAG,"그래프 프래그먼트 ${it.happiness}, ${it.neutrality}, ${it.sadness}, ${it.worry}, ${it.anger}")
        })

    }

    override fun onResume() {
        super.onResume()
        makeChart()
    }

    private fun makeChart() {

        Log.d(TAG,"makeChart $happinessCnt, $neutralityCnt, $sadnessCnt, $worryCnt, $angerCnt")

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

        if(happinessCnt>0) yValues.add(PieEntry(happinessCnt.toFloat(), "행복"))
        if(neutralityCnt>0) yValues.add(PieEntry(neutralityCnt.toFloat(), "중립"))
        if(worryCnt>0) yValues.add(PieEntry(worryCnt.toFloat(), "걱정"))
        if(sadnessCnt>0) yValues.add(PieEntry(sadnessCnt.toFloat(), "슬픔"))
        if(angerCnt>0) yValues.add(PieEntry(angerCnt.toFloat(), "분노"))


        emotion_graph.animateY(1000, Easing.EaseInOutCubic) //애니메이션

//        val description = Description()
//        description.text = "${username}님의 감정이에요" //라벨
//
//        description.textSize = 14F
//        emotion_graph.description = description

        val dataSet = PieDataSet(yValues,"")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)

        emotion_graph.data = data
    }

//    private fun dataValue(): List<PieEntry>? {
//        val dataVals: MutableList<RadarEntry> = mutableListOf()
//        dataVals.add(RadarEntry(happinessCnt.toFloat()))
//        dataVals.add(RadarEntry(neutralityCnt.toFloat()))
//        dataVals.add(RadarEntry(sadnessCnt.toFloat()))
//        dataVals.add(RadarEntry(worryCnt.toFloat()))
//        dataVals.add(RadarEntry(angerCnt.toFloat()))
//        return dataVals
//    }
//
//    //차트생성
//    private fun makeChart() {
//        val dataSet = RadarDataSet(dataValue(), "DATA")
//        dataSet.color = Color.BLUE
//        val data = RadarData()
//        data.addDataSet(dataSet)
//        val labels =
//            arrayOf("행복", "중립", "슬픔", "걱정", "분노")
//        val xAxis: XAxis = emotion_graph.xAxis
//        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
//        emotion_graph.setData(data)
//    }

}
