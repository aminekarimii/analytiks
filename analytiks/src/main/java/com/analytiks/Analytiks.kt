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
        name: String,
        vararg properties: Param,
        excludedAddons: List<Class<out BaseAnalytics>>? = null
    ) {
        clients
            .filter { addon ->
                excludedAddons?.any { it == addon.javaClass } == true
            }
            .filterIsInstance<EventsExtension>()
            .map { it.logEvent(name, *properties) }
    }

    fun userProperty(propertyName: UserProperty) = Unit

}