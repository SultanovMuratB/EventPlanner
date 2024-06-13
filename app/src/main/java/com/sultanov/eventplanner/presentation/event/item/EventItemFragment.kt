package com.sultanov.eventplanner.presentation.event.item

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
import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.presentation.Mode
import com.sultanov.eventplanner.presentation.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

internal class EventItemFragment : Fragment() {

    private val args by navArgs<EventItemFragmentArgs>()
    private lateinit var viewModel: EventViewModel

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
        viewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]
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
            getWeatherIcon()
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
            viewModel.addEventItem(
                inputName = binding.etName.text?.toString(),
                inputAddress = binding.etEventAddress.text?.toString(),
                inputCity = binding.etCityEvent.text?.toString(),
                inputDescription = binding.etDescription.text?.toString(),
                inputAction = getEventState(),
                inputTimestamp = binding.dateEvent.toLong(),
            )
            findNavController().popBackStack()
        }
    }

    private suspend fun launchEditMode() {
        viewModel.getEventMode(args.mode)
        viewModel.event.observe(viewLifecycleOwner) {
            with(binding) {
                etName.setText(it.name)
                etCityEvent.setText(it.city)
                etDescription.setText(it.description)
                etEventAddress.setText(it.address)
                when (it.action) {
                    Event.Action.VISITED -> eventVisited.toggle()
                    Event.Action.MISS -> eventMiss.toggle()
                    Event.Action.AWAIT -> eventAwait.toggle()
                }
                val date = it.timestamp.toCalendar()
                dateEvent.updateDate(
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH+1),
                    date.get(Calendar.DAY_OF_WEEK),
                )
            }
        }
        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.editEventItem(
                    inputName = binding.etName.text.toString(),
                    inputAddress = binding.etEventAddress.text.toString(),
                    inputCity = binding.etCityEvent.text.toString(),
                    inputDescription = binding.etDescription.text.toString(),
                    inputAction = getEventState(),
                    inputTimestamp = binding.dateEvent.toLong(),
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun getEventState(): Event.Action {
        return when (binding.eventGroup.checkedRadioButtonId) {
            binding.eventMiss.id -> Event.Action.MISS
            binding.eventAwait.id -> Event.Action.AWAIT
            binding.eventVisited.id -> Event.Action.VISITED
            else -> throw RuntimeException("Unknown state")
        }
    }

    private suspend fun getWeatherIcon() {
        val weatherCityItem: Weather? = viewModel.event.value?.let { getWeatherIcon(it.city) }
        val icon = weatherCityItem?.weatherIconUrl
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

    private fun Long.toCalendar() = Calendar.getInstance().apply {
        timeInMillis = this@toCalendar
    }

    private suspend fun getWeatherIcon(city: String): Weather? {
        return viewModel.getWeather(city)
    }
}