package com.analytiks.addon.mixpanel

import android.content.Context
import android.util.Log
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.UserProperty
import com.analytiks.core.formatters.JSONFormatStrategy
import com.analytiks.core.model.EventProperty
import com.mixpanel.android.mpmetrics.MixpanelAPI


const val TAG = "AnalyticsClient"

class MixpanelAnalyticsClient(
    private val configuration: MixpanelConfigurationProps
) : BaseAnalytics, EventsExtension, UserProfileExtension {

    private lateinit var mixpanelClient: MixpanelAPI
    override fun initialize(context: Context) {
        mixpanelClient = MixpanelAPI.getInstance(
            context,
            configuration.token,
            configuration.trackAutomaticEvents
        )
    }

    override fun logEvent(name: String, vararg properties: EventProperty) {
        Log.d(TAG, " Mixpanel logged event")

        if (properties.isEmpty()) {
            mixpanelClient.track(name)
            return
        }

        val formattedProps = JSONFormatStrategy().invoke(*properties)
    }

    override fun identify(userId: String?) {
        Log.d(TAG, " Mixpanel identified")
    }

    override fun setProperty(property: UserProperty) {
        Log.d(TAG, " Mixpanel Property Set")
    }
}