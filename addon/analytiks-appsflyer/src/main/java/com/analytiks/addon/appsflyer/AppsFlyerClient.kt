package com.analytiks.addon.appsflyer

import android.content.Context
import com.appsflyer.AppsFlyerLib
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.MapFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

class AppsFlyerClient(
    private val devKey: String,
    private val enableDebugLog: Boolean = false
) : CoreAddon, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {

    private lateinit var appsFlyer: AppsFlyerLib
    private lateinit var context: Context
    private val mapStrategy by lazy {
        MapFormatStrategy()
    }

    override fun initialize(context: Context) {
        this.context = context
        appsFlyer = AppsFlyerLib.getInstance()

        appsFlyer.setDebugLog(enableDebugLog)
        appsFlyer.init(devKey, null, context)
        appsFlyer.start(context, devKey)
    }

    override fun reset() {
        // AppsFlyer doesn't have a direct reset method
        // This would typically require stopping and reinitializing
    }

    override fun logEvent(name: String) {
        if (::appsFlyer.isInitialized) {
            appsFlyer.logEvent(context, name, null)
        }
    }

    override fun logEvent(name: String, vararg properties: Param) {
        if (::appsFlyer.isInitialized) {
            val eventValues = mapStrategy(*properties)
            appsFlyer.logEvent(context, name, eventValues)
        }
    }

    override fun identify(userId: String) {
        if (::appsFlyer.isInitialized) {
            appsFlyer.setCustomerUserId(userId)
        }
    }

    override fun setUserProperty(property: UserProperty) {
        if (::appsFlyer.isInitialized) {
            val additionalData = mapOf(property.propertyName to property.propertyValue)
            appsFlyer.setAdditionalData(additionalData)
        }
    }

    override fun setUserPropertyOnce(property: UserProperty) {
        setUserProperty(property)
    }

    override fun pushAll() {
        // AppsFlyer automatically handles data transmission
        // No explicit flush method needed
    }
}
