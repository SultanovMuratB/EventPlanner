package com.sultanov.eventplanner.data.repository

import com.sultanov.eventplanner.data.mapper.toItem
import com.sultanov.eventplanner.data.network.api.ApiFactory
import com.sultanov.eventplanner.data.network.api.ApiService
import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import com.sultanov.eventplanner.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val client: ApiService
) : WeatherRepository {

    override suspend fun loadCurrentWeatherCity(city: String): WeatherCityItem {
        return client.loadCurrentWeather(city).toItem()
    }
}