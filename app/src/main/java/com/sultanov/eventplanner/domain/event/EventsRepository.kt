package com.sultanov.eventplanner.domain.event

import kotlinx.coroutines.flow.Flow

internal interface EventsRepository {

    fun getEvents(): Flow<List<Event>>

    suspend fun addEvent(event: Event)

    suspend fun getEvent(eventId: Int): Event

    suspend fun editEvent(event: Event)

    suspend fun removeEvent(event: Event)
}
