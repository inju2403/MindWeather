package com.example.ttogilgi

import android.app.Application
import io.realm.Realm

class TTogilgiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}