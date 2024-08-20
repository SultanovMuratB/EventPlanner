package com.sultanov.eventplanner.presentation.event.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.databinding.FragmentWeatherCityBinding
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.presentation.core.AbstractFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

internal class WeatherCityFragment :
    AbstractFragment<FragmentWeatherCityBinding>(R.layout.fragment_weather_city) {

    private val args by navArgs<WeatherCityFragmentArgs>()

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModel.Factory(args.city)
    }

    override fun bind(view: View) = FragmentWeatherCityBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weather
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .filterNotNull().collect { weather ->
                    changeTextViews(weather)
                }
        }
    }

    private fun changeTextViews(weather: Weather) {
        with(binding) {
            cityName.text = weather.city
            currentTemp.text = String.format(
                getString(R.string.celsium),
                weather.temperature.substringBefore("."),
            )
            tempMin.text = String.format(
                getString(R.string.celsium),
                weather.minTemperature.substringBefore("."),
            )
            tempMax.text = String.format(
                getString(R.string.celsium),
                weather.maxTemperature.substringBefore("."),
            )
            windSpeed.text = String.format(
                getString(R.string.speed),
                weather.speedWind.substringBefore("."),
            )
            val icon = weather.weatherIconUrl
            Picasso.get()
                .load(icon)
                .resize(30, 30)
                .into(iconWeather)
        }
    }
}