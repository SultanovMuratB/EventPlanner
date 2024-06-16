package com.sultanov.eventplanner.data.weather

import com.google.gson.annotations.SerializedName

internal data class WeatherDto(
    @SerializedName("name")
    val city: String,
    @SerializedName("weather")
    val meta: List<Meta>,
    @SerializedName("main")
    val temperatureHolder: TemperatureHolder,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt")
    val timestamp: Long,
) {

    data class Meta(
        @SerializedName("icon")
        val weatherIconFileName: String,
    )

    data class TemperatureHolder(
        @SerializedName("temp")
        val temperature: String,
        @SerializedName("temp_min")
        val minTemperature: String,
        @SerializedName("temp_max")
        val maxTemperature: String,
    )

    data class Wind(
        @SerializedName("speed")
        val speed: String,
    )
}
