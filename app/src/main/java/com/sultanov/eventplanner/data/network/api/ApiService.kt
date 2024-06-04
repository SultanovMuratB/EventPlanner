package com.sultanov.eventplanner.data.network.api

import com.sultanov.eventplanner.data.network.dto.CurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?")
    suspend fun loadCurrentWeather(
        @Query ("q") query: String
    ) : CurrentWeatherDto
}