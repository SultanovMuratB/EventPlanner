package com.sultanov.eventplanner.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanov.eventplanner.data.local.model.EventDbModel
import com.sultanov.eventplanner.domain.entity.EventItem
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsPlannerDao {

    @Query("SELECT * FROM events_planner")
    fun getEventsPlanner() : Flow<List<EventDbModel>>

    @Query("DELETE FROM events_planner WHERE id =:eventId")
    suspend fun deleteFromEvents(eventId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToEventsPlanner(eventDbModel: EventDbModel)

    @Query("SELECT * FROM events_planner WHERE id =:id LIMIT 1 ")
    suspend fun getEventItem(id: Int) : EventItem
}