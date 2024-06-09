package com.sultanov.eventplanner.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.sultanov.eventplanner.presentation.eventItemScreen.EventItemFragment
import com.sultanov.eventplanner.presentation.eventListScreen.EventsListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ],
)
interface ApplicationComponent {

    fun inject(fragment: Fragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}