package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event

internal interface AddEventInteractor {

    suspend fun addEvent(event: Event)
}
