package com.sultanov.eventplanner.data.event.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanov.eventplanner.domain.event.Event

@Entity(tableName = "events_planner")
internal data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val name: String,
    val description: String,
    val address: String,
    val date: Long,
    val city: String,
    val action: Event.Action,
)
