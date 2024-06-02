package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository

class GetEventItemUseCase(
    private val repository: EventsRepository
) {

    operator fun invoke(eventId: Int) : EventItem =
        repository.getEventItem(eventId)
}