package com.sultanov.eventplanner.presentation

import androidx.recyclerview.widget.DiffUtil
import com.sultanov.eventplanner.domain.entity.EventItem

class EventItemDiffCallback : DiffUtil.ItemCallback<EventItem>() {

    override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return oldItem == newItem
    }
}