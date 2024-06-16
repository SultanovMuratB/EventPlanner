package com.sultanov.eventplanner.presentation.event.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sultanov.eventplanner.asEventsApplication
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import com.sultanov.eventplanner.getApplicationComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class WeatherViewModel(
    private val weatherInteractor: GetWeatherInteractor,
    private val city: String,
) : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(value = null)
    val weather: StateFlow<Weather?> = _weather

    init {
        if (city.isNotEmpty()) {
            viewModelScope.launch {
                val weather = weatherInteractor.getWeather(city)
                _weather.value = weather
            }
        } else {
            _weather.value = Weather(
                city = "Не выбран город",
                weatherIconUrl = "",
                temperature = "",
                minTemperature = "",
                maxTemperature = "",
                speedWind = "",
                timestamp = 0L,
            )
        }
    }

    companion object {

        fun Factory(city: String) = viewModelFactory {
            initializer {
                val application = get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY)
                val eventsApplication = requireNotNull(application).asEventsApplication()
                val applicationComponent = eventsApplication.getApplicationComponent()

                WeatherViewModel(
                    weatherInteractor = applicationComponent.getWeatherInteractor,
                    city = city,
                )
            }
            build()
        }
    }
}