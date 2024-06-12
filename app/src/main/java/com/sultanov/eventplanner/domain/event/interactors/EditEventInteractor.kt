package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event

internal interface EditEventInteractor {

    suspend fun editEvent(event: Event)
}
