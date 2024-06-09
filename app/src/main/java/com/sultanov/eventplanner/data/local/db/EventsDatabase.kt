package com.sultanov.eventplanner.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sultanov.eventplanner.data.local.model.EventDbModel

@Database(entities = [EventDbModel::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {

    abstract fun eventsPlannerDao(): EventsPlannerDao

    companion object {

        private const val DB_NAME = "EventsDatabase"
        private var INSTANCE: EventsDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): EventsDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
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