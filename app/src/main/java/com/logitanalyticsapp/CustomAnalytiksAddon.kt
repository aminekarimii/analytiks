package com.logitanalyticsapp

import android.content.Context
import android.util.Log
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

class CustomAnalytiksAddon : CoreAddon,
    EventsExtension,
    UserProfileExtension,
    AnalyticsDataTransmitterExtension {
    override fun initialize(context: Context) {
        Log.d("GlobalTag", "CustomAnalytiksAddon has been initialized");
    }

    override fun reset() {
        Log.d("GlobalTag", "CustomAnalytiksAddon has been reset");
    }

    override fun logEvent(name: String) = Unit

    override fun logEvent(name: String, vararg properties: Param) = Unit

    override fun identify(userId: String) = Unit

    override fun setUserProperty(property: UserProperty) = Unit

    override fun setUserPropertyOnce(property: UserProperty) = Unit

    override fun pushAll() = Unit
}