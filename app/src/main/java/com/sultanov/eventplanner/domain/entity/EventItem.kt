package com.sultanov.eventplanner.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class EventItem(
    var id: Int = UNDEFINED_ID,
    val name: String,
    val descriptionEvent: String,
    val address: String,
    val cityEvent: String,
    val event: Event,
    val date: Calendar,
) : Parcelable {

    companion object {
        const val UNDEFINED_ID = -1
    }
}