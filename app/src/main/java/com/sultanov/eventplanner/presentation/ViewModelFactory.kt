package com.sultanov.eventplanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

private typealias ViewModelProviders =
        @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>

internal class ViewModelFactory @Inject constructor(
    private val viewModelProviders: ViewModelProviders,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}
