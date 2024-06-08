package com.sultanov.eventplanner.presentation.eventItemScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.sultanov.eventplanner.databinding.FragmentEventItemBinding
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import com.sultanov.eventplanner.presentation.eventListScreen.EventListViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class EventItemFragment : Fragment() {

    private val args by navArgs<EventItemFragmentArgs>()
    private lateinit var viewModel: EventListViewModel

    private var _binding: FragmentEventItemBinding? = null
    private val binding: FragmentEventItemBinding
        get() = _binding ?: throw RuntimeException("FragmentEventItemBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[EventListViewModel::class.java]
        viewModel.getEventMode(args.mode)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.eventItemLD.observe(viewLifecycleOwner) {
            with(binding) {
                etName.setText(it.name)
                etDescription.setText(it.descriptionEvent)
                etCityEvent.setText(it.cityEvent)
                etEventAddress.setText(it.address)
                val day = it.date.get(Calendar.DAY_OF_WEEK)
                val month = it.date.get(Calendar.MONTH + 1)
                val year = it.date.get(Calendar.YEAR)
                dateEvent.init(year, month, day, null)
                when (it.event) {
                    Event.VISITED -> eventVisited.isEnabled
                    Event.MISS -> eventMiss.isEnabled
                    Event.AWAIT -> eventAwait.isEnabled
                }
            }
        }
        viewModel.viewModelScope.launch {
            val weatherCityItem = viewModel.eventItemLD.value?.let { getWeatherIcon(it.cityEvent) }
            val icon = weatherCityItem?.weather
            Picasso.get()
                .load(icon)
                .resize(30, 30)
                .into(binding.weatherIcon)
            if (weatherCityItem != null) {
                binding.weatherIcon.setOnClickListener {
                    findNavController()
                        .navigate(
                            EventItemFragmentDirections
                                .actionEventItemFragmentToWeatherCityFragment(weatherCityItem)
                        )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getWeatherIcon(city: String): WeatherCityItem {
        val weatherCityItem = viewModel.loadCurrentWeatherCity(city)
        return weatherCityItem
    }
}