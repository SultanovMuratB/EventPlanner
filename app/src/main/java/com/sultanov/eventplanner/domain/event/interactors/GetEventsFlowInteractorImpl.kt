package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetEventsFlowInteractorImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : GetEventsFlowInteractor {

    override fun getEvents(): Flow<List<Event>> {
        return eventsRepository.getEvents()
    }
}
