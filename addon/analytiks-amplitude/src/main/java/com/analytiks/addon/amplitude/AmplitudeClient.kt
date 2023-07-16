package com.analytiks.addon.amplitude

import android.content.Context
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.events.Identify
import com.amplitude.common.Logger
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.MapFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

class AmplitudeClient(
    private val token: String,
    private val serverGeoZone: ServerGeoZone = ServerGeoZone.EU,
    private val optOut: Boolean = false,
    private val minTimeBetweenSessionsMillis: Long = 10000
) : CoreAddon, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {
    private lateinit var amplitude: Amplitude
    private val mapStrategy by lazy {
        MapFormatStrategy()
    }

    override fun initialize(context: Context) {
        amplitude = Amplitude(
            Configuration(
                apiKey = token,
                context = context,
                serverZone = serverGeoZone.serverZone,
                optOut = optOut,
                minTimeBetweenSessionsMillis = minTimeBetweenSessionsMillis
            )
        )

        amplitude.logger.logMode = Logger.LogMode.DEBUG
    }

    override fun reset() {
        amplitude.reset()
    }

    override fun logEvent(name: String) {
        amplitude.track(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        amplitude.track(name, mapStrategy(*properties))
    }

    override fun identify(userId: String) {
        amplitude.setUserId(userId)
    }

    override fun setUserProperty(property: UserProperty) {
        val identify = Identify()
        property.propertyValue?.let {
            identify.set(
                property.propertyName,
                it
            )
        }
        amplitude.identify(identify)
    }

    override fun setUserPropertyOnce(property: UserProperty) = Unit
    override fun pushAll() {
        amplitude.flush()
    }
}
