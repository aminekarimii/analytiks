package com.analytiks.addon.appvisor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.analytiks.core.EventLog
import com.analytiks.core.VisorEvent

class AppVisorDataCollector private constructor() {
    private val _events = MutableLiveData<List<String>>()
    private val events: LiveData<List<String>> = _events

    fun addEvent(event: VisorEvent) {
        val formattedEvent = formatEvent(event)
        _events.value = _events.value?.plus(formattedEvent) ?: listOf(formattedEvent)
    }

    fun getEvents(): LiveData<List<String>> {
        return events
    }


    private fun formatEvent(event: VisorEvent): String {
        return when (event.type) {
            is EventLog.Event -> {
                (event.type as EventLog.Event).properties.joinToString { param -> "${param.propertyName} : ${param.propertyValue}" }
            }

            EventLog.Reset -> "Reset"

            EventLog.InitializeService -> "Service initialized"
            EventLog.PushEvents -> "Push events"
            EventLog.UserIdentification -> "User identified"
            EventLog.UserPropertyUpdate -> "User property updated"
            else -> "No properties"
        }
    }

    companion object {
        @Volatile
        private var instance: AppVisorDataCollector? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: AppVisorDataCollector().also { instance = it }
            }
    }
}