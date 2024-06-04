package com.sultanov.eventplanner.domain.entity

import java.util.Calendar

data class WeatherCityItem(
    val name: String,
    val weather: String,
    val main: String,
    val dt: Calendar,
)
