package com.sultanov.eventplanner.domain.usecase

import androidx.lifecycle.LiveData
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository

class GetEventsListUseCase(
    private val repository: EventsRepository
) {

    operator fun invoke() : LiveData<List<EventItem>> = repository.getEventsList()
}