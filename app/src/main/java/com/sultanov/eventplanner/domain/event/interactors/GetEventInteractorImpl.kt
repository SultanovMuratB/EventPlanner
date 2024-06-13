package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import javax.inject.Inject

internal class GetEventInteractorImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : GetEventInteractor {

    override suspend fun getEvent(eventId: Long): Event {
        return eventsRepository.getEvent(eventId)
    }
}
