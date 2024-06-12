package com.sultanov.eventplanner

import android.app.Application
import com.sultanov.eventplanner.di.DaggerApplicationComponent

internal class EventApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}