package com.analytiks.addon.mixpanel

import android.util.Log
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.UserProperty

const val TAG = "AnalyticsClient"

class MixpanelAnalyticsClient : BaseAnalytics(), EventsExtension, UserProfileExtension {
    override fun initialize() {
        Log.d(TAG, " Mixpanel initialized")
    }

    override fun logEvent(name: String) {
        Log.d(TAG, " Mixpanel logEvent")
    }

    override fun identify(userId: String?) {
        Log.d(TAG, " Mixpanel identified")
    }

    override fun setProperty(property: UserProperty) {
        Log.d(TAG, " Mixpanel Property Set")
    }
}