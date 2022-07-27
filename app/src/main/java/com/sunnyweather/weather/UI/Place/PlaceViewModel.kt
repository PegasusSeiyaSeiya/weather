package com.sunnyweather.weather.UI.Place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.weather.Logic.Model.PlaceResponse
import com.sunnyweather.weather.Logic.Repository
import retrofit2.Response

class PlaceViewModel:ViewModel() {
    private val searchLiveData=MutableLiveData<String>()
    val placeList=ArrayList<PlaceResponse.Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData){
        query -> Repository.searchPlaces(query)
    }
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
    fun savePlace(place:PlaceResponse.Place)=Repository.savePlace(place)
    fun getSavedPlace()=Repository.getSavedPlace()
    fun isPlaceSaved()=Repository.isPlaceSaved()
}