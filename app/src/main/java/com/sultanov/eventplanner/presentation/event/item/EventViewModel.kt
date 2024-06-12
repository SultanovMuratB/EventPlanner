package com.sultanov.eventplanner.presentation.event.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.interactors.AddEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.EditEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.GetEventInteractor
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import com.sultanov.eventplanner.presentation.Mode
import javax.inject.Inject

internal class EventViewModel @Inject constructor(
    private val addEventInteractor: AddEventInteractor,
    private val getEventInteractor: GetEventInteractor,
    private val editEventInteractor: EditEventInteractor,
    private val getWeatherInteractor: GetWeatherInteractor,
) : ViewModel() {

    private val _eventLD = MutableLiveData<Event>()
    val eventLD: LiveData<Event> = _eventLD

    suspend fun getEventMode(mode: Mode) {
        when (mode) {
            Mode.Add -> return
            is Mode.Edit -> {
                val item = mode.eventId
                _eventLD.value = getEventInteractor.getEvent(item)
            }
        }
    }

    suspend fun addEventItem(event: Event) {
        addEventInteractor.addEvent(event)
    }

    suspend fun editEventItem(event: Event) {
        editEventInteractor.editEvent(event)
    }

    suspend fun getWeather(city: String) : Weather? {
        return getWeatherInteractor.getWeather(city).getOrElse { cause ->
            // todo: обработать исключение? что на него делаем? cause
            null
        }
    }
}
