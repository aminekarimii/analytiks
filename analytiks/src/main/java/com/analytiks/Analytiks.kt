package com.analytiks

import android.content.Context
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

class Analytiks(
    private val clients: List<CoreAddon>
) {

    fun initialize(context: Context) {
        clients.map { it.initialize(context) }
    }

    fun logEvent(
        excludedAddons: List<Class<out CoreAddon>>? = null,
        name: String,
        vararg properties: Param
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