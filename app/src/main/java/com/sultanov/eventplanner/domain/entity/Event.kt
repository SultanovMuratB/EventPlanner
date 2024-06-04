package com.sultanov.eventplanner.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Event : Parcelable{
    VISITED, MISS, AWAIT
}