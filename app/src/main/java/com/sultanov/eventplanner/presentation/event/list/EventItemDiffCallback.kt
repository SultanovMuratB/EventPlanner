package com.sultanov.eventplanner.presentation.event.list

import androidx.recyclerview.widget.DiffUtil
import com.sultanov.eventplanner.domain.event.Event

internal class EventItemDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}