package com.sultanov.eventplanner.data.event.db

import com.sultanov.eventplanner.domain.event.Event

// todo: передалать в нормальный маппер!!!

internal fun EventEntity.toItem() : Event = Event(
    id = id,
    name = name,
    address = address,
    city = city,
    description = description,
    timestamp = date,
    action = action,
)

internal fun List<EventEntity>.toListItem() : List<Event> = map {
    it.toItem()
}

internal fun Event.toDbModel() : EventEntity = EventEntity(
    id = id,
    name = name,
    description = description,
    city = city,
    date = timestamp,
    action = action,
    address = address,
)