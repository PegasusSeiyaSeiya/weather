package com.sunnyweather.weather.UI.Weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.weather.Logic.Model.PlaceResponse
import com.sunnyweather.weather.Logic.Repository

class WeatherViewModel:ViewModel() {
    private val locationLiveData=MutableLiveData<PlaceResponse.Location>()
    var locationLng=""
    var locationLat=""
    var placeName=""
    val weatherLiveData=Transformations.switchMap(locationLiveData){
        location->Repository.refreshWeather(location.lng,location.lat)
    }
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value=PlaceResponse.Location(lng,lat)
    }
}