package com.sultanov.eventplanner.data.mapper

import com.sultanov.eventplanner.data.network.dto.CurrentWeatherDto
import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import java.util.Calendar
import java.util.Date

fun CurrentWeatherDto.toItem() : WeatherCityItem {
    return WeatherCityItem(
        name = name,
        main = main.temp,
        dt = dt.toCalendar(),
        weather = weather.first().icon.toCorrectImageUrl()
    )
}

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.toCorrectImageUrl() : String = "https://openweathermap.org/img/wn/$this@2x.png"