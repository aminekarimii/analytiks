package com.analytiks.addon.appvisor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AppVisorDataController {
    private val _events = MutableLiveData<List<String>>()
    val events: LiveData<List<String>> = _events

    fun addEvent(event: String) {
        _events.value = _events.value.orEmpty().toMutableList().apply { add(event) }
    }
}