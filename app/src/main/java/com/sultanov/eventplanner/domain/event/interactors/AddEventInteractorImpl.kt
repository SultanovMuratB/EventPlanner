package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import javax.inject.Inject

internal class AddEventInteractorImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : AddEventInteractor {

    override suspend fun addEvent(event: Event) {
        eventsRepository.addEvent(event)
    }
}
