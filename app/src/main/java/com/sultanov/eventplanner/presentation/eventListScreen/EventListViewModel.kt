package com.sultanov.eventplanner.presentation.eventListScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.entity.WeatherCityItem
import com.sultanov.eventplanner.domain.usecase.AddEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.DeleteEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.EditEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventsListUseCase
import com.sultanov.eventplanner.domain.usecase.LoadWeatherUseCase
import com.sultanov.eventplanner.presentation.Mode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class EventListViewModel @Inject constructor(
    private val deleteEventItemUseCase: DeleteEventItemUseCase,
    private val editEventItemUseCase: EditEventItemUseCase,
    private val getEventsListUseCase: GetEventsListUseCase,
) : ViewModel() {

    private val _eventList = MutableStateFlow<List<EventItem>>(listOf())
    val eventList = _eventList.asStateFlow()

    init {
        getEventList()
    }

    private fun getEventList() {
        _eventList.value = getEventsListUseCase()
    }

    suspend fun changeEventState(eventItem: EventItem) {
        var event = eventItem.event
        event = when (event) {
            Event.AWAIT -> {
                Event.MISS
            }

            Event.MISS -> {
                Event.VISITED
            }

            Event.VISITED -> {
                Event.AWAIT
            }
        }
        val newItem = eventItem.copy(event = event)
        editEventItemUseCase(newItem)
    }

    suspend fun deleteEventItem(eventItem: EventItem) {
        deleteEventItemUseCase(eventItem)
    }
}