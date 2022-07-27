package com.sunnyweather.weather.Logic.Dao

import android.content.Context
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.weather.Logic.Model.PlaceResponse
import com.sunnyweather.weather.SunnyWeatherApplication

object PlaceDao {
    fun savePlace(place:PlaceResponse.Place) {
        sharedPreferences().edit {
            putString("place", Gson(). toJson(place))
        }
    }

    fun getSavedPlace(): PlaceResponse.Place {
            val placeJson=sharedPreferences().getString("place","")
            return Gson().fromJson(placeJson, PlaceResponse.Place::class.java)
        }
        fun isPlaceSaved()=sharedPreferences().contains("place")
        private fun sharedPreferences()=SunnyWeatherApplication.context.
        getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
    }