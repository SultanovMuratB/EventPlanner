package com.sultanov.eventplanner.data.weather.api

import com.sultanov.eventplanner.data.weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ServiceApi {

    @GET("weather?APPID=2642a7374e493896a3729c45238207cb&units=metric")
    suspend fun getWeather(
        @Query("q")
        city: String,
    ): WeatherDto
}
