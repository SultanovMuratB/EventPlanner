package com.sultanov.eventplanner.data.mapper

import com.sultanov.eventplanner.data.local.model.EventDbModel
import com.sultanov.eventplanner.domain.entity.EventItem

fun EventDbModel.toItem() : EventItem = EventItem(
    id = id,
    name = name,
    address = address,
    cityEvent = cityEvent,
    descriptionEvent = descriptionEvent,
    date = date,
    event = event,
)

fun List<EventDbModel>.toListItem() : List<EventItem> = map {
    it.toItem()
}

fun EventItem.toDbModel() : EventDbModel = EventDbModel(
    id = id,
    name = name,
    descriptionEvent = descriptionEvent,
    cityEvent = cityEvent,
    date = date,
    event = event,
    address = address,
)