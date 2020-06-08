package com.example.ttogilgi.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ttogilgi.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        emotionViewModel = activity!!.application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(EmotionViewModel::class.java)
        }

        emotionViewModel!!.emotionLiveData!!.value?.let {
            happinessCnt = it.happiness
            neutralityCnt = it.neutrality
            sadnessCnt = it.sadness
            worryCnt = it.worry
            angerCnt = it.anger
        }
        
        makeChart()
    }

    private fun dataValue(): List<RadarEntry>? {
        val dataVals: MutableList<RadarEntry> = mutableListOf()
        dataVals.add(RadarEntry(happinessCnt.toFloat()))
        dataVals.add(RadarEntry(neutralityCnt.toFloat()))
        dataVals.add(RadarEntry(sadnessCnt.toFloat()))
        dataVals.add(RadarEntry(worryCnt.toFloat()))
        dataVals.add(RadarEntry(angerCnt.toFloat()))
        return dataVals
    }
    
    //차트생성
    private fun makeChart() {
        val dataSet = RadarDataSet(dataValue(), "DATA")
        dataSet.color = Color.BLUE
        val data = RadarData()
        data.addDataSet(dataSet)
        val labels =
            arrayOf("행복", "중립", "슬픔", "걱정", "분노")
        val xAxis: XAxis = emotion_graph.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        emotion_graph.data = data
    }
}
