package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository

class AddEventItemUseCase(
    private val repository: EventsRepository
) {

    suspend operator fun invoke(eventItem: EventItem) = repository.addEventItem(eventItem)
}