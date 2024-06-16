package com.sultanov.eventplanner.presentation.event.weather

import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import javax.inject.Inject

internal class WeatherViewModel @Inject constructor(
    private val getWeatherInteractor: GetWeatherInteractor
) : ViewModel() {

    suspend fun getWeather(city: String) : Weather {
        return getWeatherInteractor.getWeather(city)
    }
}