package com.analytiks.addon.appvisor.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.analytiks.addon.appvisor.databinding.ItemEventBinding
import com.analytiks.addon.appvisor.databinding.ItemInitializationBinding
import com.analytiks.addon.appvisor.ui.VisorHistoryUi.Companion.getCurrentDate
import com.analytiks.core.EventLog

enum class ViewType {
    Content, Initialization, Push
}

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val events = mutableListOf<VisorHistoryUi>()

    fun submitList(events: List<VisorHistoryUi>) {
        this.events.clear()
        this.events.addAll(events)
        notifyItemRangeChanged(0, this.events.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.Content.ordinal,
            ViewType.Push.ordinal -> {
                val binding = ItemEventBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EventViewHolder(binding)
            }

            else -> {
                val binding = ItemInitializationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                InitializationViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventViewHolder -> holder.bind(events[position])
            is InitializationViewHolder -> holder.bind(events[position].date)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (events[position].event) {
            EventLog.InitializeService -> ViewType.Initialization
            EventLog.PushEvents -> ViewType.Push
            else -> ViewType.Content
        }.ordinal
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: ItemEventBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val eventItemLayout = itemView.eventItemLayout
        private val eventName = itemView.eventName
        private val eventDateTime = itemView.eventDate
        private val icons = listOf(itemView.imag1, itemView.imag3, itemView.imag2)

        fun bind(event: VisorHistoryUi) {
            eventItemLayout.setOnClickListener { }
            eventName.text = event.event.message
            eventDateTime.text = event.date
            event.addonIcons.mapIndexed { index, icon ->
                icons[index].setImageResource(icon)
            }
        }
    }

    class InitializationViewHolder(itemView: ItemInitializationBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val initDate = itemView.initDate

        fun bind(date: String) {
            initDate.text = getCurrentDate()
        }
    }
}