package com.sunnyweather.weather.Logic.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//要使用PlaceService接口，就必须创建一个Retrofit构建器
object ServiceCreator {
    private const val BASE_URL="https://api.caiyunapp.com/"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun<T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)
    inline fun <reified T> create(): T=create(T::class.java)
}