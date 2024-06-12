package com.sultanov.eventplanner.data.weather

import com.sultanov.eventplanner.domain.weather.Weather
import javax.inject.Inject

internal class WeatherMapperImpl @Inject constructor() : WeatherMapper {

    @Throws(NoSuchElementException::class)
    override fun map(weatherDto: WeatherDto): Weather {
        return with(weatherDto) {
            val meta = weatherDto.meta.first()
            val weatherIconUrl = String.format(TEMPLATE, meta.weatherIconFileName)

            Weather(
                city = weatherDto.city,
                weatherIconUrl = weatherIconUrl,
                temperature = weatherDto.temperatureHolder.temperature,
                timestamp = weatherDto.timestamp,
            )
        }
    }

    private companion object {

        const val TEMPLATE = "https://openweathermap.org/img/wn/%1\$s@2x.png"
    }
}
