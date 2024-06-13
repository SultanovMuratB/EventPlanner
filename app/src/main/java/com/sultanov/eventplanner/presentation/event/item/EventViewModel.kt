package com.sultanov.eventplanner.presentation.event.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.interactors.AddEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.EditEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.GetEventInteractor
import com.sultanov.eventplanner.domain.weather.Weather
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import com.sultanov.eventplanner.presentation.Mode
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventViewModel @Inject constructor(
    private val addEventInteractor: AddEventInteractor,
    private val getEventInteractor: GetEventInteractor,
    private val editEventInteractor: EditEventInteractor,
    private val getWeatherInteractor: GetWeatherInteractor,
) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    suspend fun getEventMode(mode: Mode) {
        when (mode) {
            Mode.Add -> return
            is Mode.Edit -> {
                val eventId = mode.eventId
                _event.value = getEventInteractor.getEvent(eventId)
            }
        }
    }

    fun addEventItem(
        inputName: String?,
        inputDescription: String?,
        inputAddress: String?,
        inputCity: String?,
        inputAction: Event.Action,
        inputTimestamp: Long,
    ) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)
        val address = parseString(inputAddress)
        val city = parseString(inputCity)
        viewModelScope.launch {
            val item = Event(
                name = name,
                description = description,
                address = address,
                city = city,
                action = inputAction,
                timestamp = inputTimestamp,
                )
            addEventInteractor.addEvent(item)
        }
    }

    fun editEventItem(
        inputName: String?,
        inputDescription: String?,
        inputAddress: String?,
        inputCity: String?,
        inputAction: Event.Action,
        inputTimestamp: Long,
    ) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)
        val address = parseString(inputAddress)
        val city = parseString(inputCity)
        _event.value?.let {
            viewModelScope.launch {
                val item = it.copy(
                    name = name,
                    description = description,
                    address = address,
                    city = city,
                    action = inputAction,
                    timestamp = inputTimestamp,
                )
                editEventInteractor.editEvent(item)
            }
        }
    }

    suspend fun getWeather(city: String) : Weather? {
        return getWeatherInteractor.getWeather(city).getOrElse { cause ->
            // todo: обработать исключение? что на него делаем? cause
            null
        }
    }

    private fun parseString(str: String?) : String {
        return str?.trim() ?: ""
    }
}
