package com.sultanov.eventplanner.di

import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.presentation.eventItemScreen.EventItemViewModel
import com.sultanov.eventplanner.presentation.eventListScreen.EventListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @[Binds IntoMap ViewModuleKey(EventListViewModel::class)]
    fun bindEventListViewModule(viewModel: EventListViewModel) : ViewModel

    @[Binds IntoMap ViewModuleKey(EventItemViewModel::class)]
    fun bindEventItemViewModule(viewModel: EventItemViewModel) : ViewModel
}