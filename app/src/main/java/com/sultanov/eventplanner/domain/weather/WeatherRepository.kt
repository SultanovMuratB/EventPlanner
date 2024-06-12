package com.sultanov.eventplanner.domain.weather

internal interface WeatherRepository {

    @Throws(NoSuchElementException::class)
    suspend fun getWeather(city: String): Weather
}
