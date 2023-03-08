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
        name: String,
        excludedAddons: Set<Class<out CoreAddon>>? = null,
        properties: List<Param>? = null
    ) {
        clients.asSequence()
            .filter { addon ->
                excludedAddons == null || addon.javaClass !in excludedAddons
            }
            .filterIsInstance<EventsExtension>()
            .forEach {
                //TODO migrate EventsExtension::logEvent to use List instead of vararg in properties
                it.logEvent(name, *properties?.toTypedArray() ?: emptyArray())
            }
    }

    fun userProperty(propertyName: UserProperty) = Unit

}