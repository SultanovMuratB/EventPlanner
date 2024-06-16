package com.sultanov.eventplanner.data.event.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
internal interface EventsDao {

    @Query("SELECT * FROM events_planner")
    fun getEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvent(event: EventEntity)

    @Query("SELECT * FROM events_planner WHERE id =:eventId LIMIT 1")
    suspend fun readEvent(eventId: Long): EventEntity

    @Query("DELETE FROM events_planner WHERE id =:eventId")
    suspend fun deleteEvent(eventId: Long)
}
