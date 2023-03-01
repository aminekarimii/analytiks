package com.analytiks

import android.content.Context
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

class Analytiks(
    private val clients: List<BaseAnalytics>
) {

    fun initialize(context: Context) {
        clients.map { it.initialize(context) }
    }

    fun logEvent(
        name: String, vararg properties: Param,
        excludedAddons: List<BaseAnalytics>? = null
    ) {
        clients
            .filter { addon ->
                excludedAddons?.any { it == addon } == true
            }
            .filterIsInstance<EventsExtension>()
            .map { it.logEvent(name, *properties) }
    }

    fun userProperty(propertyName: UserProperty) = Unit

}