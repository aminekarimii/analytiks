package com.analytiks.addon.mixpanel

import android.content.Context
import android.util.Log
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.ConfigurationFile
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.JSONFormatStrategy
import com.analytiks.core.model.EventProperty
import com.analytiks.core.model.UserProperty
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject


const val TAG = "AnalyticsClient"

class MixpanelConfigurationProps(
    token: String,
    val optOutTrackingDefault: Boolean = false,
    val superProperties: JSONObject? = null,
    val instanceName: String? = null,
    val trackAutomaticEvents: Boolean = true,
) : ConfigurationFile(token)

class MixpanelAnalyticsClient(
    private val configuration: MixpanelConfigurationProps
) : BaseAnalytics, EventsExtension, UserProfileExtension {

    private lateinit var mixpanelClient: MixpanelAPI

    override fun initialize(context: Context) {
        mixpanelClient = MixpanelAPI.getInstance(
            context,
            configuration.token,
            configuration.optOutTrackingDefault,
            configuration.superProperties,
            configuration.instanceName,
            configuration.trackAutomaticEvents,
        )
    }

    override fun logEvent(name: String) {
        mixpanelClient.track(name)
    }

    override fun logEvent(name: String, vararg properties: EventProperty) {
        val formattedProps = JSONFormatStrategy().invoke(*properties)
        mixpanelClient.track(name, formattedProps)
    }

    override fun identify(userId: String?) {
        Log.d(TAG, " Mixpanel identified")
    }

    override fun setUserProperty(property: UserProperty) {
        Log.d(TAG, " Mixpanel Property Set")
    }
}