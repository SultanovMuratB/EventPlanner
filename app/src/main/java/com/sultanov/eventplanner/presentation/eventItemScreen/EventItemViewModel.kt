package com.sultanov.eventplanner.presentation.eventItemScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sultanov.eventplanner.data.repository.EventsRepositoryImpl
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.usecase.AddEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.EditEventItemUseCase
import com.sultanov.eventplanner.domain.usecase.GetEventItemUseCase
import java.util.Calendar

class EventItemViewModel(
    private val eventItemId: Int,
) : ViewModel() {

    private val repository = EventsRepositoryImpl

    private val addEventItemUseCase = AddEventItemUseCase(repository)
    private val editEventItemUseCase = EditEventItemUseCase(repository)
    private val getEventItemUseCase = GetEventItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit> = _shouldCloseScreen

    private val _getEventItem = MutableLiveData<EventItem>()
    val getEventItem: LiveData<EventItem> = _getEventItem

    init {
        getEventItemId(eventItemId)
    }

    private fun getEventItemId(eventItemId: Int) {
        _getEventItem.value = getEventItemUseCase(eventItemId)
    }

    suspend fun addEventItem(
        inputName: String,
        inputDescription: String,
        inputCityName: String,
        inputAddress: String,
        inputDate: Calendar,
        event: Event
    ) {
        val name = parseInput(inputName)
        val description = parseInput(inputDescription)
        val cityName = parseInput(inputCityName)
        val address = parseInput(inputAddress)
        val eventItem = EventItem(
            name = name,
            descriptionEvent = description,
            cityEvent = cityName,
            address = address,
            date = inputDate,
            event = event
        )
        addEventItemUseCase(eventItem)
        finishWork()
    }

    suspend fun editEventItem(
        inputName: String,
        inputDescription: String,
        inputCityName: String,
        inputAddress: String,
        inputDate: Calendar,
        event: Event
    ) {
        val name = parseInput(inputName)
        val description = parseInput(inputDescription)
        val cityName = parseInput(inputCityName)
        val address = parseInput(inputAddress)
        getEventItem.value?.let {
            val item = it.copy(
                name = name,
                descriptionEvent = description,
                cityEvent = cityName,
                address = address,
                date = inputDate,
                event = event
            )
            editEventItemUseCase(item)
            finishWork()
        }
    }

    private fun parseInput(input: String?) : String {
        return input?.trim() ?: ""
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }






}