package com.sultanov.eventplanner.presentation.eventItemScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.domain.entity.Event
import java.util.Calendar

class EventItemFragment : Fragment() {

    private val args by navArgs<EventItemFragmentArgs>()

    private lateinit var viewModel: EventItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var etName: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etCityEvent: TextInputEditText
    private lateinit var weatherIcon: ImageView
    private lateinit var etEventAddress: TextInputEditText
    private lateinit var date: DatePicker
    private lateinit var radioGroup: RadioGroup
    private lateinit var awaitButton: RadioButton
    private lateinit var missButton: RadioButton
    private lateinit var visitedButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_event_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EventItemViewModel::class.java]
        initViews(view)
        observeViewModel()
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        etName = view.findViewById(R.id.et_name)
        etDescription = view.findViewById(R.id.et_description)
        etCityEvent = view.findViewById(R.id.et_city_event)
        weatherIcon = view.findViewById(R.id.weather_icon)
        etEventAddress = view.findViewById(R.id.et_event_address)
        date = view.findViewById(R.id.date_event)
        radioGroup = view.findViewById(R.id.event_group)
        awaitButton = view.findViewById(R.id.event_await)
        missButton = view.findViewById(R.id.event_miss)
        visitedButton = view.findViewById(R.id.event_visited)
    }

    private fun observeViewModel() {
        viewModel.getEventItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etDescription.setText(it.descriptionEvent)
            etCityEvent.setText(it.cityEvent)
            etEventAddress.setText(it.address)
            val day = it.date.get(Calendar.DAY_OF_MONTH)
            val month = it.date.get(Calendar.MONTH + 1)
            val year = it.date.get(Calendar.YEAR)
            date.updateDate(day, month, year)
            when (it.event) {
                Event.VISITED -> visitedButton.isChecked
                Event.MISS -> missButton.isChecked
                Event.AWAIT -> awaitButton.isChecked
            }
        }
    }

    private fun isButtonChecked() : Event {
        var event = Event.AWAIT
        radioGroup.setOnCheckedChangeListener { _, checkedListener ->
            when (checkedListener) {
                R.id.event_visited -> {
                    event = Event.VISITED
                }

                R.id.event_miss -> {
                    event = Event.MISS
                }

                R.id.event_await -> {
                    event = Event.AWAIT
                }

                else -> {
                    throw RuntimeException("Invalid state")
                }
            }
        }
        return event
    }


}