package com.analytiks

import android.content.Context
import com.analytiks.core.CoreAddon
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

interface CoreAnalytiks {
    fun initialize(context: Context)
    fun reset()
    fun logEvent(name: String, excludedAddons: Set<Class<out CoreAddon>>? = null)
    fun logEvent(
        name: String,
        properties: List<Param>,
        excludedAddons: Set<Class<out CoreAddon>>? = null,
    )

    fun identify(userId: String)
    fun setUserProperty(
        property: UserProperty,
        excludedAddons: Set<Class<out CoreAddon>>? = null
    )

    fun setUserPropertyOnce(
        property: UserProperty,
        excludedAddons: Set<Class<out CoreAddon>>? = null
    )

    fun pushAll()
}