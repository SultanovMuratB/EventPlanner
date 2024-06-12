package com.sultanov.eventplanner.domain.event.interactors

import com.sultanov.eventplanner.domain.event.Event

internal interface RemoveEventInteractor {

    suspend fun removeEvent(event: Event)
}
