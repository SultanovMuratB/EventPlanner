package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import com.sultanov.eventplanner.domain.repository.WeatherRepository
import javax.inject.Inject

class LoadWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(city: String) : WeatherCityItem {
        return repository.loadCurrentWeatherCity(city)
    }
}