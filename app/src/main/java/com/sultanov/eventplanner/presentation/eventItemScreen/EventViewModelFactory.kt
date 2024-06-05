package com.sultanov.eventplanner.presentation.eventItemScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EventViewModelFactory(private val eventItemId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventItemViewModel(eventItemId)::class.java as T
    }
}