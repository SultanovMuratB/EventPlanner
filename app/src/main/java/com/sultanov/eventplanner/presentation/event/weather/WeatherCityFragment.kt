package com.sultanov.eventplanner.presentation.event.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sultanov.eventplanner.databinding.FragmentWeatherCityBinding
import com.sultanov.eventplanner.domain.weather.Weather

class WeatherCityFragment : Fragment() {

    private val args by navArgs<WeatherCityFragmentArgs>()

    private var _binding: FragmentWeatherCityBinding? = null
    private val binding: FragmentWeatherCityBinding
        get() = _binding ?: throw RuntimeException("FragmentWeatherCityBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTextViews(args.weatherCityItem)
    }

    private fun changeTextViews(weather: Weather) {
        with(binding) {
            cityName.text = weather.city
            currentTemp.text = weather.temperature.substringBefore(".")
            tempMin.text = weather.minTemperature.substringBefore(".")
            tempMax.text = weather.maxTemperature.substringBefore(".")
            windSpeed.text = weather.speedWind.substringBefore(".")
        }
    }
}