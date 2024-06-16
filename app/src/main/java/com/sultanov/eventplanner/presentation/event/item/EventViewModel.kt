package com.sultanov.eventplanner.presentation.event.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sultanov.eventplanner.asEventsApplication
import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsInteractor
import com.sultanov.eventplanner.getApplicationComponent
import com.sultanov.eventplanner.presentation.Mode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class EventViewModel(
    private val eventsInteractor: EventsInteractor,
    private val eventMode: Mode,
) : ViewModel() {

    private val mutableEventFlow = MutableStateFlow<Event?>(value = null)
    val eventFlow: StateFlow<Event?> = mutableEventFlow

    private var eventId = 0L

    init {
        if (eventMode is Mode.Edit) {
            eventMode.eventId.let { id ->
                viewModelScope.launch {
                    val event = eventsInteractor.getEvent(id)
                    eventId = event.id
                    mutableEventFlow.value = event
                }
            }
        }
    }

    fun saveEvent(event: Event) {
        viewModelScope.launch {
            when (eventMode) {
                Mode.Add -> eventsInteractor.addEvent(event)
                is Mode.Edit -> {
                    val e = event.copy(
                        id = eventId,
                    )
                    eventsInteractor.editEvent(e)
                }
            }
        }
    }

    companion object {

        fun Factory(eventMode: Mode) = viewModelFactory {
            initializer {
                val application = get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY)
                val eventsApplication = requireNotNull(application).asEventsApplication()
                val applicationComponent = eventsApplication.getApplicationComponent()

                EventViewModel(
                    eventsInteractor = applicationComponent.eventsInteractor,
                    eventMode = eventMode
                )
            }
            build()
        }
    }
}
