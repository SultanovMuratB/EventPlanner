package com.sultanov.eventplanner.presentation.eventItemScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.presentation.eventListScreen.EventListViewModel
import com.sultanov.eventplanner.presentation.eventListScreen.EventsListFragment
import java.util.Calendar

class EventItemFragment : Fragment() {

    private val args by navArgs<EventItemFragmentArgs>()
    private lateinit var viewModel: EventListViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[EventListViewModel::class.java]
        viewModel.getEventMode(args.mode)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_event_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModel.eventItemLD.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etDescription.setText(it.descriptionEvent)
            etCityEvent.setText(it.cityEvent)
            etEventAddress.setText(it.address)
            val day = it.date.get(Calendar.DAY_OF_WEEK)
            val month = it.date.get(Calendar.MONTH + 1)
            val year = it.date.get(Calendar.YEAR)
            date.init(year, month, day, null)
            when (it.event) {
                Event.VISITED -> visitedButton.isEnabled
                Event.MISS -> missButton.isEnabled
                Event.AWAIT -> awaitButton.isEnabled
            }
        }
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
}