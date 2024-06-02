package com.sultanov.eventplanner.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.data.repository.EventsRepositoryImpl
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.usecase.AddEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.DeleteEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.EditEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventsListUseCase

class MainViewModel : ViewModel() {

    private val repository = EventsRepositoryImpl

    private val addEventItemUseCase = AddEventItemUseCase(repository)
    private val deleteEventItemUseCase = DeleteEventItemUseCase(repository)
    private val editEventItemUseCase = EditEventItemUseCase(repository)
    private val getEventsListUseCase = GetEventsListUseCase(repository)
    private val getEventItemUseCase = GetEventItemUseCase(repository)

    private val _shopList = getEventsListUseCase.invoke()
    val shopList: LiveData<List<EventItem>> = _shopList

    fun changeEventState(eventItem: EventItem) {
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
        editEventItemUseCase.invoke(newItem)
    }

    fun deleteEventItem(eventItem: EventItem) {
        deleteEventItemUseCase.invoke(eventItem)
    }
}