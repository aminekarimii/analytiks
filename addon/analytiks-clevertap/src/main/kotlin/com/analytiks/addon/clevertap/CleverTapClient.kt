package com.analytiks.addon.clevertap

import android.content.Context
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.MapFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.clevertap.android.sdk.CleverTapAPI

class CleverTapClient(
    private val enableDebugLog: Boolean = false
) : CoreAddon, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {

    private lateinit var cleverTap: CleverTapAPI
    private val mapStrategy by lazy {
        MapFormatStrategy()
    }

    override fun initialize(context: Context) {
        CleverTapAPI.setDebugLevel(if (enableDebugLog) CleverTapAPI.LogLevel.VERBOSE else CleverTapAPI.LogLevel.OFF)
        cleverTap = CleverTapAPI.getDefaultInstance(context) ?: throw IllegalStateException("CleverTap initialization failed")
    }

    override fun reset() {
        if (::cleverTap.isInitialized) {
            cleverTap.pushProfile(mapOf("Identity" to null))
        }
    }

    override fun logEvent(name: String) {
        if (::cleverTap.isInitialized) {
            cleverTap.pushEvent(name)
        }
    }

    override fun logEvent(name: String, vararg properties: Param) {
        if (::cleverTap.isInitialized) {
            val eventProperties = mapStrategy(*properties)
            cleverTap.pushEvent(name, eventProperties)
        }
    }

    override fun identify(userId: String) {
        if (::cleverTap.isInitialized) {
            val profile = mapOf("Identity" to userId)
            cleverTap.onUserLogin(profile)
        }
    }

    override fun setUserProperty(property: UserProperty) {
        if (::cleverTap.isInitialized) {
            val profile = mapOf(property.propertyName to property.propertyValue)
            cleverTap.pushProfile(profile)
        }
    }

    override fun setUserPropertyOnce(property: UserProperty) {
        setUserProperty(property)
    }

    override fun pushAll() {
        if (::cleverTap.isInitialized) {
            cleverTap.pushEvent("flush")
        }
    }
}

