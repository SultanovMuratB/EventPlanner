package com.sultanov.eventplanner.di

import com.sultanov.eventplanner.EventsApplication
import com.sultanov.eventplanner.domain.event.EventsInteractor
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
    ],
)
internal interface ApplicationComponent : AndroidInjector<EventsApplication> {

    val eventsInteractor: EventsInteractor

    val getWeatherInteractor: GetWeatherInteractor

    @Component.Factory
    interface Factory : AndroidInjector.Factory<EventsApplication>
}

