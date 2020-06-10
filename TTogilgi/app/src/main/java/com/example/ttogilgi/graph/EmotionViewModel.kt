package com.example.ttogilgi.graph

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttogilgi.model.Emotion
import com.example.ttogilgi.utils.Constants

class EmotionViewModel: ViewModel() {
    private var emotion = Emotion()
    val emotionLiveData: MutableLiveData<Emotion> by lazy {
        MutableLiveData<Emotion>().apply {
            value = emotion
        }
    }
    fun setEmotions(newEmotion: Emotion) {
        emotion.happiness = newEmotion.happiness
        emotion.neutrality = newEmotion.neutrality
        emotion.sadness = newEmotion.sadness
        emotion.worry = newEmotion.worry
        emotion.anger = newEmotion.anger
        Log.d(Constants.TAG,"${emotion.happiness}, ${emotion.neutrality}, ${emotion.sadness}, ${emotion.worry}, ${emotion.anger}")
//        emotion = newEmotion
        emotionLiveData.value = emotion
    }
}