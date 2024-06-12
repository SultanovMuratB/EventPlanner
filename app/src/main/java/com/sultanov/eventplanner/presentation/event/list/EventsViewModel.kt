package com.sultanov.eventplanner.presentation.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.interactors.EditEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.GetEventsFlowInteractor
import com.sultanov.eventplanner.domain.event.interactors.RemoveEventInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventsViewModel @Inject constructor(
    private val removeEventInteractor: RemoveEventInteractor,
    private val editEventInteractor: EditEventInteractor,
    private val getEventsFlowInteractor: GetEventsFlowInteractor,
) : ViewModel() {

    private val _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> = _eventList

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getEventsFlowInteractor.getEvents()
                .collect {
                    _eventList.value = it
                }
        }
    }

    suspend fun changeEventState(event: Event) {
        val action = when (event.action) {
            Event.Action.AWAIT -> Event.Action.MISS
            Event.Action.MISS -> Event.Action.VISITED
            Event.Action.VISITED -> Event.Action.AWAIT
        }

        val newEvent = event.copy(action = action)
        editEventInteractor.editEvent(newEvent)
    }

    suspend fun deleteEventItem(event: Event) {
        removeEventInteractor.removeEvent(event)
    }
}