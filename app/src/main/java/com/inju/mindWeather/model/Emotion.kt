package com.inju.mindWeather.model

data class Emotion (
    var happiness: Double = 0.0,
    var neutrality: Double = 0.0,
    var sadness: Double = 0.0,
    var worry: Double = 0.0,
    var anger: Double = 0.0
)