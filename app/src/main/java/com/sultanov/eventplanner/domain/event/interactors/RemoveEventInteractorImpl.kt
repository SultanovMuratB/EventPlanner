package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import javax.inject.Inject

internal class RemoveEventInteractorImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : RemoveEventInteractor {

    override suspend fun removeEvent(event: Event) {
        eventsRepository.removeEvent(event)
    }
}
