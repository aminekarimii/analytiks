package com.analytiks

import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProperty

class Analytiks constructor(private val clients: List<BaseAnalytics>) {

    fun initialize() {
        clients.map { it.initialize() }
    }

    fun logEvent(name: String) {
        clients.filterIsInstance<EventsExtension>().map { it.logEvent(name) }
    }

    fun userProperty(propertyName: UserProperty) = Unit

}