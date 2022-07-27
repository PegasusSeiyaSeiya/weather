package com.sunnyweather.weather.Logic

import android.util.Log
import androidx.lifecycle.liveData
import com.sunnyweather.weather.Logic.Dao.PlaceDao
import com.sunnyweather.weather.Logic.Model.PlaceResponse
import com.sunnyweather.weather.Logic.Model.Weather
import com.sunnyweather.weather.Logic.Network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.Exception
import kotlin.coroutines.CoroutineContext

//仓库层代码，用来判断调用方请求的数据应该是从本地数据源中获取还是从网络数据源中获取（该代码只考虑了网络请求），并将获得的数据返回给调用方
object Repository {
    fun searchPlaces(query:String)= fire(Dispatchers.IO){
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status=="ok"){
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("回复状态是${placeResponse.status}"))
            }
        }
        fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO){
                coroutineScope {
                    val deferredRealtime=async {
                        SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                    }
                    val deferredDaily=async{
                        SunnyWeatherNetwork.getDailyWeather(lng,lat)
                    }
                        val realtimeResponse=deferredRealtime.await()
                        val dailyResponse=deferredDaily.await()
                    if (realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                        val weather= Weather(realtimeResponse.result.realtime,
                            dailyResponse.result.daily)
                        Result.success(weather)
                    }else{
                        Result.failure(
                            RuntimeException(
                                "realtime response status is ${realtimeResponse.status}"
                                        +"daily response " +
                                        " is ${dailyResponse.status}"
                            )
                        )
                    }
                    }
                }

    private fun <T> fire(context:CoroutineContext,block:suspend () -> Result<T>)=
        liveData<Result<T>>(context){
        val result=try{
            block()
        }catch (e:Exception){
            Result.failure<T>(e)
        }
            emit(result)
    }
    fun savePlace(place: PlaceResponse.Place)=PlaceDao.savePlace(place)
    fun getSavedPlace()=PlaceDao.getSavedPlace()
    fun isPlaceSaved()=PlaceDao.isPlaceSaved()
}