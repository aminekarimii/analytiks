package com.analytiks.addon.appvisor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.analytiks.core.EventLog
import com.analytiks.core.VisorEvent

class AppVisorDataCollector private constructor() {
    private val _events = MutableLiveData<List<VisorHistoryUi>>()
    private val events: LiveData<List<VisorHistoryUi>> = _events

    fun addEvent(event: VisorEvent) {
        _events.value = _events.value?.plus(VisorHistoryUi.from(event)) ?: listOf(VisorHistoryUi.from(event))
    }

    fun getEvents(): LiveData<List<VisorHistoryUi>> {
        return events
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