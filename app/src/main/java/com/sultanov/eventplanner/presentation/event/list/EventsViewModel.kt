package com.sultanov.eventplanner.presentation.event.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sultanov.eventplanner.asEventsApplication
import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsInteractor
import com.sultanov.eventplanner.getApplicationComponent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class EventsViewModel(
    private val eventsInteractor: EventsInteractor,
) : ViewModel() {

    val eventsFlow: StateFlow<List<Event>> = eventsInteractor.getEvents().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf(),
    )

    fun changeEventState(event: Event) {
        viewModelScope.launch {
            val action = when (event.action) {
                Event.Action.AWAIT -> Event.Action.MISS
                Event.Action.MISS -> Event.Action.VISITED
                Event.Action.VISITED -> Event.Action.AWAIT
            }

            val newEvent = event.copy(action = action)
            eventsInteractor.editEvent(newEvent)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventsInteractor.removeEvent(event)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY)
                val eventsApplication = requireNotNull(application).asEventsApplication()
                val applicationComponent = eventsApplication.getApplicationComponent()

                EventsViewModel(
                    eventsInteractor = applicationComponent.eventsInteractor,
                )
            }

            build()
        }
    }
}