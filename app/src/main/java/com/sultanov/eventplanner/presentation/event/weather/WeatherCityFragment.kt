package com.sultanov.eventplanner.presentation.event.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.sultanov.eventplanner.EventApp
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.databinding.FragmentWeatherCityBinding
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.presentation.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class WeatherCityFragment : Fragment() {

    private val args by navArgs<WeatherCityFragmentArgs>()
    private lateinit var viewModel: WeatherViewModel

    private var _binding: FragmentWeatherCityBinding? = null
    private val binding: FragmentWeatherCityBinding
        get() = _binding ?: throw RuntimeException("FragmentWeatherCityBinding == null")

    @Inject
    lateinit var factory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as EventApp).component
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
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
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
        val city = args.city
        binding.cityName.text = city
        lifecycleScope.launch {
            val city = args.city
            val weather = viewModel.getWeather(city)
            println(weather.toString())
            changeViews(weather)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeViews(weather: Weather) {
        with(binding) {
            cityName.text = weather.city
            currentTemp.text = String.format(
                getString(R.string.celsium),
                weather.temperature.substringBefore(".")
            )
            tempMin.text = String.format(
                getString(R.string.celsium),
                weather.minTemperature.substringBefore(".")
            )
            tempMax.text = String.format(
                getString(R.string.celsium),
                weather.maxTemperature.substringBefore(".")
            )
            windSpeed.text = String.format(
                getString(R.string.fast_wind),
                weather.speedWind.substringBefore(".")
            )
            val icon = weather.weatherIconUrl
            Picasso.get()
                .load(icon)
                .resize(30, 30)
                .into(weatherCityIcon)
        }
    }
}