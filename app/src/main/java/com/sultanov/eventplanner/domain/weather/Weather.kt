package com.sultanov.eventplanner.domain.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Weather(
    val city: String,
    val weatherIconUrl: String,
    val temperature: String,
    val minTemperature: String,
    val maxTemperature: String,
    val speedWind: String,
    val timestamp: Long,
) : Parcelable
