package com.sultanov.eventplanner.presentation.event.item

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.presentation.core.AbstractFragment
import com.sultanov.eventplanner.databinding.FragmentEventItemBinding
import com.sultanov.eventplanner.domain.event.Event
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.util.Calendar

internal class EventItemFragment :
    AbstractFragment<FragmentEventItemBinding>(R.layout.fragment_event_item) {

    private val args by navArgs<EventItemFragmentArgs>()

    private val viewModel: EventViewModel by viewModels {
        EventViewModel.Factory(args.eventMode)
    }

    override fun bind(view: View) = FragmentEventItemBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .filterNotNull().collect { event ->
                    with(binding) {
                        etName.setText(event.name)
                        etCityEvent.setText(event.city)
                        val city = etCityEvent.text.toString()
                        navigateToWeather(city)
                        etDescription.setText(event.description)
                        etEventAddress.setText(event.address)
                        when (event.action) {
                            Event.Action.VISITED -> eventVisited.toggle()
                            Event.Action.MISS -> eventMiss.toggle()
                            Event.Action.AWAIT -> eventAwait.toggle()
                        }
                        val date = event.timestamp.toCalendar()
                        dateEvent.updateDate(
                            date.get(Calendar.YEAR),
                            date.get(Calendar.MONTH + 1),
                            date.get(Calendar.DAY_OF_WEEK),
                        )
                    }
                }
        }

        with(binding) {
            saveButton.setOnClickListener {
                viewModel.saveEvent(
                    Event(
                        name = etName.text.toString(),
                        address = etEventAddress.text.toString(),
                        city = etCityEvent.text.toString(),
                        description = etDescription.text.toString(),
                        timestamp = dateEvent.toLong(),
                        action = getEventState(),
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun getEventState(): Event.Action {
        return with(binding) {
            when (eventGroup.checkedRadioButtonId) {
                eventMiss.id -> Event.Action.MISS
                eventAwait.id -> Event.Action.AWAIT
                eventVisited.id -> Event.Action.VISITED
                else -> throw IllegalStateException()
            }
        }
    }

    private fun navigateToWeather(city: String) {
        binding.weatherIcon.setOnClickListener {
            findNavController()
                .navigate(
                    EventItemFragmentDirections
                        .actionEventItemFragmentToWeatherCityFragment(city)
                )
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
}