package com.sultanov.eventplanner.domain.weather.interactors

import com.sultanov.eventplanner.domain.weather.Weather

internal interface GetWeatherInteractor {

    suspend fun getWeather(city: String): Result<Weather>
}
