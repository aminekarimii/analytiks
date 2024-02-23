package com.analytiks.addon.appvisor.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.analytiks.addon.appvisor.databinding.ItemEventBinding
import java.util.Calendar
import java.util.Date

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    private val events = mutableListOf<String>()

    fun submitList(events: List<String>) {
        this.events.clear()
        this.events.addAll(events)
        notifyItemRangeChanged(0, this.events.lastIndex)
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
        private val eventItemLayout = itemView.eventItemLayout
        private val eventName = itemView.eventName
        private val eventDateTime = itemView.eventDate
        private val currentTime: Date = Calendar.getInstance().time

        fun bind(event: String) {
            eventItemLayout.setOnClickListener {  }
            eventName.text = event
            eventDateTime.text = currentTime.toString()
        }
    }
}