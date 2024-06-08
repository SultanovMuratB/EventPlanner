package com.sultanov.eventplanner.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class WeatherCityItem(
    val name: String,
    val weather: String,
    val temp: String,
    val dt: Calendar,
) : Parcelable
