package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository

class EditEventItemUseCase(
    private val repository: EventsRepository
) {

    operator fun invoke(eventItem: EventItem) = repository.editEventItem(eventItem)
}