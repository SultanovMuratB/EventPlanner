package com.sultanov.eventplanner.domain.repository

import com.sultanov.eventplanner.domain.entity.EventItem
import kotlinx.coroutines.flow.Flow

interface EventsRepository {

    fun getEventsList() : Flow<List<EventItem>>

    suspend fun addEventItem(eventItem: EventItem)

    suspend fun deleteEventItem(eventItem: EventItem)

    suspend fun editEventItem(eventItem: EventItem)

    suspend fun getEventItem(eventId: Int) : EventItem
}