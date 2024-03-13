package com.analytiks.addon.appvisor.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.analytiks.addon.appvisor.databinding.ItemEventBinding
import com.analytiks.addon.appvisor.databinding.ItemInitializationBinding
import com.analytiks.addon.appvisor.databinding.ItemPushBinding
import com.analytiks.core.EventLog

enum class ViewType {
    Content, Initialization, Push
}

class EventsAdapter(
    private val onItemClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val events = mutableListOf<VisorHistoryUi>()

    fun submitList(events: List<VisorHistoryUi>) {
        this.events.clear()
        this.events.addAll(events)
        notifyItemRangeChanged(0, this.events.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.Push.ordinal -> {
                PushViewHolder(
                    ItemPushBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ViewType.Content.ordinal -> {
                EventViewHolder(
                    ItemEventBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
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
            is PushViewHolder -> holder.bind(events[position])
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

    class EventViewHolder(itemView: ItemEventBinding, val onItemClick: () -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
        private val eventItemLayout = itemView.eventItemLayout
        private val eventName = itemView.eventName
        private val eventDateTime = itemView.eventDate
        private val icons = listOf(itemView.imag1, itemView.imag2, itemView.imag3)

        fun bind(event: VisorHistoryUi) {
            eventItemLayout.setOnClickListener {
                onItemClick()
            }
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
            initDate.text = date
        }
    }

    class PushViewHolder(itemView: ItemPushBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val initDate = itemView.eventDate
        private val icons = listOf(itemView.imag1, itemView.imag2, itemView.imag3)

        fun bind(event: VisorHistoryUi) {
            initDate.text = event.date
            event.addonIcons.mapIndexed { index, icon ->
                icons[index].setImageResource(icon)
            }
        }
    }
}