package com.sultanov.eventplanner.presentation.eventItemScreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.sultanov.eventplanner.EventApp
import com.sultanov.eventplanner.databinding.FragmentEventItemBinding
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import com.sultanov.eventplanner.presentation.Mode
import com.sultanov.eventplanner.presentation.eventListScreen.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class EventItemFragment : Fragment() {

    private val args by navArgs<EventItemFragmentArgs>()
    private lateinit var viewModel: EventItemViewModel

    private var _binding: FragmentEventItemBinding? = null
    private val binding: FragmentEventItemBinding
        get() = _binding ?: throw RuntimeException("FragmentEventItemBinding == null")

    @Inject
    lateinit var factory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as EventApp).component
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[EventItemViewModel::class.java]
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
        lifecycleScope.launch {
            launchMode()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun launchMode() {
        when (args.mode) {
            Mode.Add -> launchAddMode()
            is Mode.Edit -> launchEditMode()
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
           lifecycleScope.launch {
                viewModel.addEventItem(
                    EventItem(
                        name = binding.etName.text.toString(),
                        address = binding.etEventAddress.text.toString(),
                        cityEvent = binding.etCityEvent.text.toString(),
                        descriptionEvent = binding.etDescription.text.toString(),
                        date = binding.dateEvent.toLong(),
                        event = getEventState(),
                    )
                )
               findNavController().popBackStack()
            }
        }
    }

    private suspend fun launchEditMode() {
        viewModel.getEventMode(args.mode)
        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.editEventItem(
                    EventItem(
                        name = binding.etName.text.toString(),
                        address = binding.etEventAddress.text.toString(),
                        cityEvent = binding.etCityEvent.text.toString(),
                        descriptionEvent = binding.etDescription.text.toString(),
                        date = binding.dateEvent.toLong(),
                        event = getEventState(),
                    )
                )
            }
        }
    }

    private fun getEventState(): Event {
        return when (binding.eventGroup.checkedRadioButtonId) {
            binding.eventMiss.id -> Event.MISS
            binding.eventAwait.id -> Event.AWAIT
            binding.eventVisited.id -> Event.VISITED
            else -> throw RuntimeException("Unknown state")
        }
    }

    private suspend fun getWeatherIcon() {
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

    private fun DatePicker.toLong(): Long {
        return Calendar.getInstance().apply {
            set(year, month + 1, dayOfMonth)
        }.timeInMillis
    }

    private suspend fun getWeatherIcon(city: String): WeatherCityItem {
        return viewModel.loadCurrentWeatherCity(city)
    }
}