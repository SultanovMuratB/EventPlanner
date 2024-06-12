package com.sultanov.eventplanner.data.weather

import com.sultanov.eventplanner.domain.weather.Weather

internal interface WeatherMapper {

    @Throws(NoSuchElementException::class)
    fun map(weatherDto: WeatherDto): Weather
}
