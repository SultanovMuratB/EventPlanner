package com.sultanov.eventplanner.presentation.eventListScreen

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.usecase.DeleteEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.EditEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventsListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventListViewModel @Inject constructor(
    private val deleteEventItemUseCase: DeleteEventItemUseCase,
    private val editEventItemUseCase: EditEventItemUseCase,
    private val getEventsListUseCase: GetEventsListUseCase,
) : ViewModel() {

    private val _eventList = MutableLiveData<List<EventItem>>()
    val eventList: LiveData<List<EventItem>> = _eventList

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getEventsListUseCase()
                .collect {
                    _eventList.value = it
                }
        }
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