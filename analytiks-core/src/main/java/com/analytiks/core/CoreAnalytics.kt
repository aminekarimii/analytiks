package com.analytiks.core

import android.content.Context
import android.os.Bundle
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import java.util.*


interface BaseAnalytics {
    fun initialize(context: Context)
}

interface EventsExtension {
    fun logEvent(name: String)
    fun logEvent(name: String, vararg properties: Param)
}

interface UserProfileExtension {
    fun identify(userId: String? = UUID.randomUUID().toString())
    fun setUserProperty(property: UserProperty)
}

/**
 * An interface for handling the communication with the analytics server. Clients that implement this
 * interface can use it to flush any pending events, users, or updated user props to the server.
 */
interface AnalyticsDataTransmitterExtension {

    /**
     * Flushes any pending events to the analytics server. This method should be called to ensure that
     * all events are sent to the server before the client shuts down.
     */
    fun pushAll()
}

