package com.analytiks.addon.appvisor.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.analytiks.addon.appvisor.databinding.ItemEventBinding

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    private val events = mutableListOf<String>()

    fun addEvent(event: String) {
        events.add(event)
        notifyItemInserted(events.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: ItemEventBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val eventName = itemView.eventName
        fun bind(event: String) {
            eventName.text = event
        }
    }
}