package com.sultanov.eventplanner.data.repository

import androidx.lifecycle.LiveData
import com.sultanov.eventplanner.data.local.db.EventsPlannerDao
import com.sultanov.eventplanner.data.mapper.toDbModel
import com.sultanov.eventplanner.data.mapper.toListItem
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val eventsPlannerDao: EventsPlannerDao,
) : EventsRepository {

    override fun getEventsList(): List<EventItem> {
        return eventsPlannerDao.getEventsPlanner().toListItem()
    }

    override suspend fun addEventItem(eventItem: EventItem) {
        eventsPlannerDao.addToEventsPlanner(eventItem.toDbModel())
    }

    override suspend fun deleteEventItem(eventItem: EventItem) {
        eventsPlannerDao.deleteFromEvents(eventItem.id)
    }

    override suspend fun editEventItem(eventItem: EventItem) {
        eventsPlannerDao.addToEventsPlanner(eventItem.toDbModel())
    }

    override suspend fun getEventItem(eventId: Int): EventItem {
        return eventsPlannerDao.getEventItem(eventId)
    }
}