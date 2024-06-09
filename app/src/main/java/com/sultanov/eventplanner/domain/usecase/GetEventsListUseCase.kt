package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsListUseCase @Inject constructor(
    private val repository: EventsRepository
) {

    operator fun invoke() : List<EventItem> = repository.getEventsList()
}