package com.sultanov.eventplanner.presentation.eventListScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sultanov.eventplanner.R
import com.sultanov.eventplanner.domain.entity.Event
import com.sultanov.eventplanner.domain.entity.EventItem

class EventListAdapter : ListAdapter<EventItem, EventListAdapter.EventItemViewHolder>(
    EventItemDiffCallback()
) {

    var onEventIconClickListener: ((EventItem) -> Unit)? = null
    var onEventItemClickListener: ((EventItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_VISIT -> R.layout.item_event_visit
            VIEW_TYPE_MISS -> R.layout.item_event_miss
            VIEW_TYPE_AWAIT -> R.layout.item_event_await
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return EventItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.event) {
            Event.VISITED -> VIEW_TYPE_VISIT
            Event.MISS -> VIEW_TYPE_MISS
            Event.AWAIT -> VIEW_TYPE_AWAIT
        }
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        val eventItem = getItem(position)
        holder.itemView.setOnClickListener {
            onEventItemClickListener?.invoke(eventItem)
        }
        holder.itemName.text = eventItem.name
        holder.address.text = eventItem.address
        holder.cityEvent.text = eventItem.cityEvent
        holder.event.setOnClickListener {
            onEventIconClickListener?.invoke(eventItem)
        }
    }

    companion object {

        const val VIEW_TYPE_MISS = 100
        const val VIEW_TYPE_AWAIT = 101
        const val VIEW_TYPE_VISIT = 102

        const val MAX_POOL_SIZE = 30
    }

    class EventItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemName)
        val address: TextView = view.findViewById(R.id.address)
        val cityEvent: TextView = view.findViewById(R.id.cityEvent)
        val event: ImageView = view.findViewById(R.id.imageEvent)
    }
}
