package com.sultanov.eventplanner.data.event.db

import com.sultanov.eventplanner.domain.event.Event
import javax.inject.Inject

internal class EventsMapperImpl @Inject constructor() : EventsMapper {

    override fun mapToItem(eventEntity: EventEntity): Event {
        return Event(
            id = eventEntity.id,
            name = eventEntity.name,
            address = eventEntity.address,
            city = eventEntity.city,
            description = eventEntity.description,
            timestamp = eventEntity.date,
            action = eventEntity.action,
        )
    }

    override fun mapToEntity(event: Event): EventEntity {
        return EventEntity(
            id = event.id,
            name = event.name,
            address = event.address,
            city = event.city,
            description = event.description,
            date = event.timestamp,
            action = event.action,
        )
    }

    override fun mapToList(listEntity: List<EventEntity>): List<Event> {
        return listEntity.map { mapToItem(it) }
    }
}