package com.inju.mindWeather.graph

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inju.mindWeather.model.Emotion

class EmotionViewModel1year: ViewModel() {
    private var emotion = Emotion()
    val emotionLiveData: MutableLiveData<Emotion> by lazy {
        MutableLiveData<Emotion>().apply {
            value = emotion
        }
    }
    fun setEmotions(newEmotion: Emotion) {
        emotion = newEmotion
        emotionLiveData.value = emotion
    }
}