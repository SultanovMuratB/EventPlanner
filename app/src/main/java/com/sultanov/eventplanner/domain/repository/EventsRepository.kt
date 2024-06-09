package com.sultanov.eventplanner.domain.repository

import androidx.lifecycle.LiveData
import com.sultanov.eventplanner.domain.entity.EventItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EventsRepository {

    fun getEventsList() : List<EventItem>

    suspend fun addEventItem(eventItem: EventItem)

    suspend fun deleteEventItem(eventItem: EventItem)

    suspend fun editEventItem(eventItem: EventItem)

    suspend fun getEventItem(eventId: Int) : EventItem
}