package com.sultanov.eventplanner.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem
import com.sultanov.eventplanner.domain.repository.EventsRepository

object EventsRepositoryImpl : EventsRepository {

    private val eventListLD = MutableLiveData<List<EventItem>>()

    private val eventList = sortedSetOf<EventItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    private val array = arrayOf(Event.MISS, Event.AWAIT, Event.VISITED)

    init {
        for (i in 0 until 1000) {
            val item = EventItem(
                id = i,
                name = "name $i",
                descriptionEvent = "description event $i",
                address = "address $i",
                cityEvent = "city event $i",
                event = array[(0..2).random()]
            )
            addEventItem(item)
        }
    }

    override fun getEventsList(): LiveData<List<EventItem>> {
        return eventListLD
    }

    override fun addEventItem(eventItem: EventItem) {
        if (eventItem.id == EventItem.UNDEFINED_ID) {
            eventItem.id = autoIncrementId++
        }
        eventList.add(eventItem)
        updateList()
    }

    override fun deleteEventItem(eventItem: EventItem) {
        eventList.remove(eventItem)
        updateList()
    }

    override fun editEventItem(eventItem: EventItem) {
        val oldElement = getEventItem(eventItem.id)
        eventList.remove(oldElement)
        addEventItem(eventItem)
    }

    override fun getEventItem(eventId: Int): EventItem {
        return eventList.find {
            it.id == eventId
        } ?: throw RuntimeException("Element with id $eventId not found")
    }

    private fun updateList() {
        eventListLD.value = eventList.toList()
    }

}