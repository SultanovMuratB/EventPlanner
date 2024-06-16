package com.sultanov.eventplanner.domain.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Event(
    val id: Long = UNDEFINED_ID,
    val name: String,
    val description: String,
    val address: String,
    val city: String,
    val action: Action,
    val timestamp: Long,
) : Parcelable {

    @Parcelize
    internal enum class Action : Parcelable {

        VISITED,

        MISS,

        AWAIT
    }

    private companion object {

        const val UNDEFINED_ID: Long = 0
    }
}
