package com.sultanov.eventplanner.domain.usecase

import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsListUseCase @Inject constructor(
    private val repository: EventsRepository
) {

    operator fun invoke() : Flow<List<EventItem>> = flow {
        repository.getEventsList()
    }
}