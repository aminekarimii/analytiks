package com.analytiks.addon.appvisor.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.analytiks.core.VisorEvent

class AppVisorDataCollector private constructor() {
    private val _events = MutableLiveData<List<VisorEvent>>()
    private val events: LiveData<List<VisorEvent>> = _events

    fun addEvent(event: VisorEvent) {
        Log.d("SpecialTag", event.toString())
        _events.value = _events.value?.plus(event) ?: listOf(event)
    }

    fun getEvents(): LiveData<List<VisorEvent>> {
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