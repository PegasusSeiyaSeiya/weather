package com.sunnyweather.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log

class SunnyWeatherApplication:Application() {
    companion object {
        const val TOKEN="3usfQNk7vdnjiFRB"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
       override fun onCreate(){
            super.onCreate()
            context=applicationContext
        }
    }