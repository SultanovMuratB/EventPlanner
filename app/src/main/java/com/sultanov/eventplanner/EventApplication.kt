package com.sultanov.eventplanner

import android.app.Application
import com.sultanov.eventplanner.di.ApplicationComponent
import com.sultanov.eventplanner.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

internal class EventsApplication : DaggerApplication() {

    val applicationInjector: AndroidInjector<EventsApplication> by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return applicationInjector
    }
}

internal fun Application.asEventsApplication(): EventsApplication =
    this as EventsApplication

internal fun EventsApplication.getApplicationComponent(): ApplicationComponent =
    applicationInjector as ApplicationComponent