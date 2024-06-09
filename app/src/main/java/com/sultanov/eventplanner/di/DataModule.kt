package com.sultanov.eventplanner.di

import android.app.Application
import com.sultanov.eventplanner.data.local.db.EventsDatabase
import com.sultanov.eventplanner.data.local.db.EventsPlannerDao
import com.sultanov.eventplanner.data.network.api.ApiFactory
import com.sultanov.eventplanner.data.network.api.ApiService
import com.sultanov.eventplanner.data.repository.EventsRepositoryImpl
import com.sultanov.eventplanner.data.repository.WeatherRepositoryImpl
import com.sultanov.eventplanner.domain.repository.EventsRepository
import com.sultanov.eventplanner.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindEventsRepository(impl: EventsRepositoryImpl) : EventsRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl) : WeatherRepository

    companion object {

        @[ApplicationScope Provides]
        fun provideApiService() : ApiService {
            return ApiFactory.apiService
        }

        @[ApplicationScope Provides]
        fun provideEventListDao(
            application: Application
        ) : EventsPlannerDao {
            return EventsDatabase.getInstance(application).eventsPlannerDao()
        }
    }
}