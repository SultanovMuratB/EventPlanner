package com.sultanov.eventplanner.data.weather

import com.sultanov.eventplanner.data.weather.api.ServiceApi
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.domain.weather.WeatherRepository
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {

    @Throws(NoSuchElementException::class)
    override suspend fun getWeather(city: String): Weather {
        val weatherDto = serviceApi.getWeather(city)
        val weather = weatherMapper.map(weatherDto)

        return weather
    }
}
