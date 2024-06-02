package com.sultanov.eventplanner.domain.repository

import androidx.lifecycle.LiveData
import com.sultanov.eventplanner.domain.entity.EventItem

interface EventsRepository {

    fun getEventsList() : LiveData<List<EventItem>>

    fun addEventItem(eventItem: EventItem)

    fun deleteEventItem(eventItem: EventItem)

    fun editEventItem(eventItem: EventItem)

    fun getEventItem(eventId: Int) : EventItem
}