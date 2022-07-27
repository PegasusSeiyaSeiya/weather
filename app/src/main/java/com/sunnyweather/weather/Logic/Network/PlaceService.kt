package com.sunnyweather.weather.Logic.Network
import retrofit2.Call
import com.sunnyweather.weather.Logic.Model.PlaceResponse
import com.sunnyweather.weather.SunnyWeatherApplication
import retrofit2.http.GET
import retrofit2.http.Query
//访问彩云天气城市搜索API的Retrofit的接口
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>
}
