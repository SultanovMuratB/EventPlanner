package com.sultanov.eventplanner.di

import android.app.Application
import androidx.lifecycle.ViewModel
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
import com.sultanov.eventplanner.domain.event.EventsRepository
import com.sultanov.eventplanner.domain.event.interactors.AddEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.AddEventInteractorImpl
import com.sultanov.eventplanner.domain.event.interactors.EditEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.EditEventInteractorImpl
import com.sultanov.eventplanner.domain.event.interactors.GetEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.GetEventInteractorImpl
import com.sultanov.eventplanner.domain.event.interactors.GetEventsFlowInteractor
import com.sultanov.eventplanner.domain.event.interactors.GetEventsFlowInteractorImpl
import com.sultanov.eventplanner.domain.event.interactors.RemoveEventInteractor
import com.sultanov.eventplanner.domain.event.interactors.RemoveEventInteractorImpl
import com.sultanov.eventplanner.domain.weather.WeatherRepository
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractor
import com.sultanov.eventplanner.domain.weather.interactors.GetWeatherInteractorImpl
import com.sultanov.eventplanner.presentation.event.item.EventViewModel
import com.sultanov.eventplanner.presentation.event.list.EventsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ApplicationModule.BindsModule::class,
    ],
)
internal object ApplicationModule {

    @ApplicationScope
    @Provides
    fun provideServiceApi(
        serviceApiFactory: ServiceApiFactory,
    ): ServiceApi {
        return serviceApiFactory.create()
    }

    @ApplicationScope
    @Provides
    fun provideEventsDao(
        application: Application
    ): EventsDao {
        return EventsDatabase.getInstance(application).eventsDao()
    }

    @Suppress("unused")
    @Module
    interface BindsModule {

        @ApplicationScope
        @Binds
        fun bindServiceApiFactory(impl: ServiceApiFactoryImpl): ServiceApiFactory

        @ApplicationScope
        @Binds
        fun bindEventsRepository(impl: EventsRepositoryImpl): EventsRepository

        @ApplicationScope
        @Binds
        fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

        @ApplicationScope
        @Binds
        fun bindAddEventInteractor(impl: AddEventInteractorImpl): AddEventInteractor

        @ApplicationScope
        @Binds
        fun bindGetEventInteractor(impl: GetEventInteractorImpl): GetEventInteractor

        @ApplicationScope
        @Binds
        fun bindEditEventInteractor(impl: EditEventInteractorImpl): EditEventInteractor

        @ApplicationScope
        @Binds
        fun bindRemoveEventInteractor(impl: RemoveEventInteractorImpl): RemoveEventInteractor

        @ApplicationScope
        @Binds
        fun bindGetEventsFlowInteractor(impl: GetEventsFlowInteractorImpl): GetEventsFlowInteractor

        @ApplicationScope
        @Binds
        fun bindGetWeatherInteractor(impl: GetWeatherInteractorImpl): GetWeatherInteractor

        @ApplicationScope
        @Binds
        fun bindWeatherMapper(impl: WeatherMapperImpl): WeatherMapper

        @ApplicationScope
        @Binds
        fun bindEventsMapper(impl: EventsMapperImpl) : EventsMapper

        @ApplicationScope
        @Binds
        @IntoMap
        @ViewModelKey(EventsViewModel::class)
        fun bindEventsViewModel(vm: EventsViewModel): ViewModel

        @ApplicationScope
        @Binds
        @IntoMap
        @ViewModelKey(EventViewModel::class)
        fun bindEventViewModel(vm: EventViewModel): ViewModel
    }
}
