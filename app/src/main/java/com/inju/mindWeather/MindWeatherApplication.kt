package com.inju.mindWeather

import android.app.Application
import io.realm.Realm

class MindWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}