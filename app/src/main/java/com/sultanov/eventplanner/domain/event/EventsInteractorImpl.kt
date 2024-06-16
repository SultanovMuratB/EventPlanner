package com.sultanov.eventplanner.domain.event

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class EventsInteractorImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : EventsInteractor {

    override fun getEvents(): Flow<List<Event>> {
        return eventsRepository.getEvents()
    }

    override suspend fun addEvent(event: Event) {
        eventsRepository.addEvent(event)
    }

    override suspend fun getEvent(eventId: Long): Event {
        return eventsRepository.getEvent(eventId)
    }

    override suspend fun editEvent(event: Event) {
        eventsRepository.editEvent(event)
    }

    override suspend fun removeEvent(event: Event) {
        eventsRepository.removeEvent(event)
    }
}
