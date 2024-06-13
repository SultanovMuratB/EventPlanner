package com.sultanov.eventplanner.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface Mode : Parcelable {

    @Parcelize
    data object Add : Mode

    @Parcelize
    data class Edit(val eventId: Long) : Mode

}
