package com.sultanov.eventplanner.presentation.event.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.databinding.FragmentWeatherCityBinding
import com.sultanov.eventplanner.domain.weather.Weather
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

internal class WeatherCityFragment : Fragment() {

    private val args by navArgs<WeatherCityFragmentArgs>()

    private var _binding: FragmentWeatherCityBinding? = null
    private val binding: FragmentWeatherCityBinding
        get() = _binding ?: throw RuntimeException("FragmentWeatherCityBinding == null")

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModel.Factory(args.city)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.weather.filterNotNull().collect { weather ->
                    changeTextViews(weather)
                }
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