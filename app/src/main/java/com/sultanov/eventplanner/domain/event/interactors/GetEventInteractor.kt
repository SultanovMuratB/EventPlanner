package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event

internal interface GetEventInteractor {

    suspend fun getEvent(eventId: Long): Event
}
