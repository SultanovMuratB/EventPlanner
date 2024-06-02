package com.sultanov.eventplanner.domain.entity

data class EventItem(
    var id: Int = UNDEFINED_ID,
    val name: String,
    val descriptionEvent: String,
    val address: String,
    val cityEvent: String,
    val event: Event,
) {

    companion object {
        const val UNDEFINED_ID = -1
    }
}