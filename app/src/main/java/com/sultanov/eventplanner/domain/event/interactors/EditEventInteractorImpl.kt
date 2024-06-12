package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import javax.inject.Inject

internal class EditEventInteractorImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : EditEventInteractor {

    override suspend fun editEvent(event: Event) {
        eventsRepository.editEvent(event)
    }
}
