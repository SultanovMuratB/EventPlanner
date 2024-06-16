package com.sultanov.eventplanner.data.event.db

import com.sultanov.eventplanner.domain.event.Event

internal interface EventsMapper {

    fun mapToItem(eventEntity: EventEntity) : Event

    fun mapToEntity(event: Event) : EventEntity

    fun mapToList(listEntity: List<EventEntity>) : List<Event>
}

