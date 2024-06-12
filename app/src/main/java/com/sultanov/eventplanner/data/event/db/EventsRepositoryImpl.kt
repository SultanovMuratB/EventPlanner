package com.sultanov.eventplanner.data.event.db

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EventsRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
) : EventsRepository {

    override fun getEvents(): Flow<List<Event>> {
        return eventsDao.getEvents().map {
            it.toListItem()
        }
    }

    override suspend fun addEvent(event: Event) {
        eventsDao.createEvent(event.toDbModel())
    }

    override suspend fun getEvent(eventId: Int): Event {
        return eventsDao.readEvent(eventId).toItem()
    }

    override suspend fun editEvent(event: Event) {
        eventsDao.updateEvent(event.toDbModel())
    }

    override suspend fun removeEvent(event: Event) {
        eventsDao.deleteEvent(event.id)
    }
}
