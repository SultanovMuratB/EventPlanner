package com.sultanov.eventplanner.domain.weather.interactors

import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.domain.weather.WeatherRepository
import javax.inject.Inject

internal class GetWeatherInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetWeatherInteractor {

    override suspend fun getWeather(city: String): Weather {
        return weatherRepository.getWeather(city)
    }
}
