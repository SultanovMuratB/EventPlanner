package com.sultanov.eventplanner.di

import android.app.Application
import com.sultanov.eventplanner.EventsApplication
import com.sultanov.eventplanner.data.event.db.EventsDao
import com.sultanov.eventplanner.data.event.db.EventsDatabase
import com.sultanov.eventplanner.data.event.db.EventsMapper
import com.sultanov.eventplanner.data.event.db.EventsMapperImpl
import com.sultanov.eventplanner.data.event.db.EventsRepositoryImpl
import com.sultanov.eventplanner.data.weather.WeatherMapper
import com.sultanov.eventplanner.data.weather.WeatherMapperImpl
import com.sultanov.eventplanner.data.weather.WeatherRepositoryImpl
import com.sultanov.eventplanner.data.weather.api.ServiceApi
import com.sultanov.eventplanner.data.weather.api.ServiceApiFactory
import com.sultanov.eventplanner.data.weather.api.ServiceApiFactoryImpl
import com.sultanov.eventplanner.domain.event.EventsInteractor
import com.sultanov.eventplanner.domain.event.EventsInteractorImpl
import com.sultanov.eventplanner.domain.event.EventsRepository
import com.sultanov.eventplanner.domain.weather.WeatherRepository
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ApplicationModule.BindsModule::class,
    ],
)
internal object ApplicationModule {

    @Provides
    @Singleton
    fun eventsDao(application: Application): EventsDao {
        return EventsDatabase.getInstance(application).eventsDao()
    }

    @Provides
    @Singleton
    fun serviceApi(serviceApiFactory: ServiceApiFactory): ServiceApi {
        return serviceApiFactory.create()
    }

    @Suppress("unused")
    @Module
    interface BindsModule {

        @Binds
        @Singleton
        fun application(impl: EventsApplication): Application

        @Binds
        @Singleton
        fun serviceApiFactory(impl: ServiceApiFactoryImpl): ServiceApiFactory

        @Binds
        @Singleton
        fun eventsRepository(impl: EventsRepositoryImpl): EventsRepository

        @Binds
        @Singleton
        fun weatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

        @Binds
        @Singleton
        fun eventsInteractor(impl: EventsInteractorImpl): EventsInteractor

        @Binds
        @Singleton
        fun getWeatherInteractor(impl: GetWeatherInteractorImpl): GetWeatherInteractor

        @Binds
        @Singleton
        fun weatherMapper(impl: WeatherMapperImpl): WeatherMapper

        @Binds
        @Singleton
        fun eventMapper(impl: EventsMapperImpl) : EventsMapper
    }
}
