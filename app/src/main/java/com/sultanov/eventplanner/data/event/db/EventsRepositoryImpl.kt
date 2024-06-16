package com.sultanov.eventplanner.data.event.db

import com.sultanov.eventplanner.domain.event.Event
import com.sultanov.eventplanner.domain.event.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EventsRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
    private val eventsMapper: EventsMapper,
) : EventsRepository {

    override fun getEvents(): Flow<List<Event>> {
        val flowEntity = eventsDao.getEvents()
        val flowItem = flowEntity.map { eventsMapper.mapToList(it) }

        return flowItem
    }

    override suspend fun addEvent(event: Event) {
        val eventDb = eventsMapper.mapToEntity(event)
        eventsDao.createEvent(eventDb)
    }

    override suspend fun getEvent(eventId: Long): Event {
        val eventDb = eventsDao.readEvent(eventId)
        val event = eventsMapper.mapToItem(eventDb)

        return event
    }

    override suspend fun editEvent(event: Event) {
        val eventDb = eventsMapper.mapToEntity(event)
        eventsDao.createEvent(eventDb)
    }

    override suspend fun removeEvent(event: Event) {
        eventsDao.deleteEvent(event.id)
    }
}
