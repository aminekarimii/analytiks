package com.analytiks.addon.onesignal

import android.content.Context
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.MapFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

class OneSignalClient(
    private val appId: String,
    private val enableDebugLog: Boolean = false
) : CoreAddon, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {

    private val mapStrategy by lazy {
        MapFormatStrategy()
    }

    override fun initialize(context: Context) {
        if (enableDebugLog) {
            OneSignal.Debug.logLevel = LogLevel.VERBOSE
        }
        OneSignal.initWithContext(context, appId)
    }

    override fun reset() {
        OneSignal.logout()
    }

    override fun logEvent(name: String) {
        OneSignal.Session.addOutcome(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        val eventProperties = mapStrategy(*properties)
        OneSignal.User.addTags(eventProperties.mapValues { it.value.toString() })
        OneSignal.Session.addOutcome(name)
    }

    override fun identify(userId: String) {
        OneSignal.login(userId)
    }

    override fun setUserProperty(property: UserProperty) {
        OneSignal.User.addTag(property.propertyName, property.propertyValue.toString())
    }

    override fun setUserPropertyOnce(property: UserProperty) {
        setUserProperty(property)
    }

    override fun pushAll() {
    }
}

