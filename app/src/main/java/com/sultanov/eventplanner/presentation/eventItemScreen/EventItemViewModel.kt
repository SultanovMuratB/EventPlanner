package com.sultanov.eventplanner.presentation.eventItemScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import com.sultanov.eventplanner.domain.usecase.AddEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.EditEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.LoadWeatherUseCase
import com.sultanov.eventplanner.presentation.Mode
import javax.inject.Inject

class EventItemViewModel @Inject constructor(
    private val editEventItemUseCase: EditEventItemUseCase,
    private val getEventItemUseCase: GetEventItemUseCase,
    private val addEventItemUseCase: AddEventItemUseCase,
    private val loadWeatherUseCase: LoadWeatherUseCase,
) : ViewModel() {

    private val _eventItemLD = MutableLiveData<EventItem>()
    val eventItemLD: LiveData<EventItem> = _eventItemLD

    suspend fun getEventMode(mode: Mode) {
        when (mode) {
            Mode.Add -> return
            is Mode.Edit -> {
                val item = mode.eventId
                _eventItemLD.value = getEventItemUseCase(item)
            }
        }
    }

    suspend fun addEventItem(eventItem: EventItem) {
        addEventItemUseCase(eventItem)
    }

    suspend fun editEventItem(eventItem: EventItem) {
        editEventItemUseCase(eventItem)
    }

    suspend fun loadCurrentWeatherCity(city: String) : WeatherCityItem {
        return loadWeatherUseCase(city)
    }
}