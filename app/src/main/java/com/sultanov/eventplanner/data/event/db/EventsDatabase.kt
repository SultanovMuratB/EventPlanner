package com.sultanov.eventplanner.data.event.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// todo: посмотреть что улучшить!!!

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
internal abstract class EventsDatabase : RoomDatabase() {

    abstract fun eventsDao(): EventsDao

    companion object {

        private const val DB_NAME = "EventsDatabase"
        private var INSTANCE: EventsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): EventsDatabase {
            INSTANCE?.let { return it }

            synchronized(lock) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = EventsDatabase::class.java,
                    name = DB_NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}