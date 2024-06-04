package com.sultanov.eventplanner.data.network.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("name") val name: String,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Temp,
    @SerializedName("dt") val dt:Long,
)
