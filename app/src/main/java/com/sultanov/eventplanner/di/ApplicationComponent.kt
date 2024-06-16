package com.sultanov.eventplanner.di

import android.app.Application
import com.sultanov.eventplanner.presentation.event.item.EventItemFragment
import com.sultanov.eventplanner.presentation.event.list.EventsListFragment
import com.sultanov.eventplanner.presentation.event.weather.WeatherCityFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class,
    ],
)
internal interface ApplicationComponent {

    fun inject(fragment: EventsListFragment)

    fun inject(fragment: EventItemFragment)

    fun inject(fragment: WeatherCityFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}

