package com.analytiks

import android.content.Context
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProperty

class Analytiks(
    private val clients: List<BaseAnalytics>
) {

    fun initialize(context: Context) {
        clients.map { it.initialize(context) }
    }

    fun logEvent(name: String) {
        clients.filterIsInstance<EventsExtension>().map { it.logEvent(name) }
    }

    fun userProperty(propertyName: UserProperty) = Unit

}