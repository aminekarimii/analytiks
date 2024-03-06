package com.analytiks.addon.timber

import android.content.Context
import android.util.Log
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import timber.log.Timber

const val TAG = "TimberLocalClient"

class TimberLocalClient : CoreAddon, EventsExtension, UserProfileExtension {
    override fun initialize(context: Context) {
        Timber.plant(Timber.DebugTree())
    }

    override fun reset() {
        Timber.tag(TAG).log(Log.INFO, "Reset called")
    }

    override fun logEvent(name: String) {
        Timber.tag(TAG).log(Log.INFO, "Event: $name")
    }

    override fun logEvent(name: String, vararg properties: Param) {
        val logMessage = if (properties.isNotEmpty()) {
            val propertyStrings = properties.mapIndexed { index, param ->
                "${index + 1}. ${param.propertyName} : ${param.propertyValue}"
            }.joinToString("\n")

            "***** Event: $name -> props:\n$propertyStrings\n*****"
        } else {
            "***** Event: $name (No properties) *****"
        }

        Timber.tag(TAG).log(Log.INFO, logMessage)
    }
    override fun identify(userId: String) {
        Timber.tag(TAG).log(Log.INFO, "User has been identified by: $userId")
    }

    override fun setUserProperty(property: UserProperty) {
        Timber.tag(TAG).log(Log.INFO, "User property has been added: $property")
    }

    override fun setUserPropertyOnce(property: UserProperty) {
        Timber.tag(TAG).log(Log.INFO, "User property has been added once: $property")
    }

}