package com.sunnyweather.weather.Logic.Model

data class Weather(val realtime:RealtimeResponse.Realtime,val daily: DailyResponse.Daily) {
}