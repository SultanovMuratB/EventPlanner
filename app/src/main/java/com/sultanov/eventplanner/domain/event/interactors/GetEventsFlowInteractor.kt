package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event
import kotlinx.coroutines.flow.Flow

internal interface GetEventsFlowInteractor {

    fun getEvents(): Flow<List<Event>>
}
