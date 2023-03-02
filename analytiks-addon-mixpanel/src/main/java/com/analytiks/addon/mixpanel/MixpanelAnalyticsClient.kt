package com.analytiks.addon.mixpanel

import android.content.Context
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.JSONFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject


const val TAG = "MixpanelAnalyticsClient"

class MixpanelAnalyticsClient(
    private val token: String,
    private val optOutTrackingDefault: Boolean = false,
    private val superProperties: JSONObject? = null,
    private val instanceName: String? = null,
    private val trackAutomaticEvents: Boolean = true,
) : BaseAnalytics, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {

    private lateinit var mixpanelClient: MixpanelAPI

    override fun initialize(context: Context) {
        mixpanelClient = MixpanelAPI.getInstance(
            context,
            this.token,
            this.optOutTrackingDefault,
            this.superProperties,
            this.instanceName,
            this.trackAutomaticEvents,
        )
    }

    override fun logEvent(name: String) {
        mixpanelClient.track(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        val formattedProps = JSONFormatStrategy().invoke(*properties)
        mixpanelClient.track(name, formattedProps)
    }

    override fun identify(userId: String) {
        mixpanelClient.identify(userId, true)
    }

    override fun setUserProperty(property: UserProperty) {
        if (mixpanelClient.people.isIdentified) {
            mixpanelClient.people.set(property.propertyName, property.propertyValue)
        }
    }

    override fun setUserPropertyOnce(property: UserProperty) {
        if (mixpanelClient.people.isIdentified) {
            mixpanelClient.people.setOnce(property.propertyName, property.propertyValue)
        }
    }

    override fun pushAll() {
        mixpanelClient.flush()
    }
}