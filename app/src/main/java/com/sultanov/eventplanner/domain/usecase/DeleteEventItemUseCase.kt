package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository
import javax.inject.Inject

class DeleteEventItemUseCase @Inject constructor(
    private val repository: EventsRepository
) {

    suspend operator fun invoke(eventItem: EventItem) = repository.deleteEventItem(eventItem)
}