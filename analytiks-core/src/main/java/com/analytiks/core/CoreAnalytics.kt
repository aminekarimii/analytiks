package com.analytiks.core

import android.content.Context
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty


/**
 * An interface for initializing an analytics client. Clients that implement this interface can be
 * initialized with a context object to set up any necessary configurations and connections to
 * the analytics service.
 */
interface CoreAddon {

    /**
     * Initializes the analytics client with the given context. This method should be called once
     * during the application's startup process to set up any necessary configurations and
     * connections to the analytics service.
     *
     * @param context the context object to use for initialization.
     */
    fun initialize(context: Context)

    fun reset()
}

/**
 * An optional extension interface that can be implemented by analytics clients to provide support
 * for custom event tracking. Implementing this interface enables logging of custom events with
 * additional parameters that can be used for analysis and reporting.
 */
interface EventsExtension {

    /**
     * Logs an event with the given name. The event is treated as a custom event and is associated
     * with the current user and any applicable context, such as a screen or session. The event does
     * not have any additional parameters or properties.
     *
     * @param name the name of the event to log.
     */
    fun logEvent(name: String)

    /**
     * Logs an event with the given name and additional parameters. The event is treated as a custom
     * event and is associated with the current user and any applicable context, such as a screen
     * or session. The additional parameters are specified as key-value pairs of strings, and can be
     * used for segmentation and analysis.
     *
     * @param name the name of the event to log.
     * @param properties the additional properties to associate with the event, as key-value pairs
     *                   of strings.
     */
    fun logEvent(name: String, vararg properties: Param)
}

/**
 * An optional extension interface that can be implemented by analytics clients to provide support
 * for user profile tracking. Implementing this interface enables tracking of user identities
 * and properties that can be used for segmentation and analysis.
 */
interface UserProfileExtension {
    /**
     * Identifies the current user with the given user ID. This method should be called whenever
     * the user ID changes, such as when the user logs in or logs out.
     *
     * @param userId the unique identifier for the user, such as an email address or a user ID
     *               assigned by a backend service.
     */
    fun identify(userId: String)

    /**
     * Sets the value of a user property to the given value. User properties are key-value pairs
     * that can be used to segment user data and perform analysis. Calling this method with the
     * same property key multiple times will overwrite the previous value of the property.
     *
     * @param property the user property to set, including its key and value.
     */
    fun setUserProperty(property: UserProperty)

    /**
     * Sets the value of a user property to the given value, but only if the property has not been
     * set before. Calling this method with the same property key multiple times will have no effect
     * after the first call, and subsequent calls will not overwrite the previous value of the
     * property.
     *
     * @param property the user property to set, including its key and value.
     */
    fun setUserPropertyOnce(property: UserProperty)
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

