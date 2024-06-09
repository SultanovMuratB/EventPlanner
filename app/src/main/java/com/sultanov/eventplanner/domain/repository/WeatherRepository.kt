package com.sultanov.eventplanner.domain.repository

import com.sultanov.eventplanner.domain.entity.WeatherCityItem

interface WeatherRepository {

    suspend fun loadCurrentWeatherCity(city: String) : WeatherCityItem
}