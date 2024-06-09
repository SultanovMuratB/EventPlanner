package com.sultanov.eventplanner.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanov.eventplanner.domain.entity.Event
import java.util.Date

@Entity(tableName = "events_planner")
data class EventDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val descriptionEvent: String,
    val address: String,
    val date: Long,
    val cityEvent: String,
    val event: Event
)
