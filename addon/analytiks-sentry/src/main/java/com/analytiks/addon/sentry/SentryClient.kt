package com.analytiks.addon.sentry

import android.content.Context
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid
import io.sentry.protocol.User

class SentryClient(
    private val dsn: String,
    private val environment: String? = null,
    private val release: String? = null,
    private val enableDebug: Boolean = false,
    private val tracesSampleRate: Double? = null
) : CoreAddon, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {

    override fun initialize(context: Context) {
        SentryAndroid.init(context) { options ->
            options.dsn = dsn
            environment?.let { options.environment = it }
            release?.let { options.release = it }
            options.isDebug = enableDebug
            tracesSampleRate?.let { options.tracesSampleRate = it }
        }
    }

    override fun reset() {
        Sentry.configureScope { scope ->
            scope.user = null
            scope.clear()
        }
    }

    override fun logEvent(name: String) {
        Sentry.captureMessage(name, SentryLevel.INFO)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        Sentry.configureScope { scope ->
            properties.forEach { param ->
                scope.setTag(param.propertyName, param.propertyValue)
            }
        }
        Sentry.captureMessage(name, SentryLevel.INFO)
    }

    override fun identify(userId: String) {
        Sentry.setUser(User().apply {
            id = userId
        })
    }

    override fun setUserProperty(property: UserProperty) {
        Sentry.configureScope { scope ->
            val currentUser = scope.user ?: User()
            when (property.propertyName.lowercase()) {
                "email" -> currentUser.email = property.propertyValue?.toString()
                "username" -> currentUser.username = property.propertyValue?.toString()
                "ip_address", "ipaddress" -> currentUser.ipAddress =
                    property.propertyValue?.toString()

                else -> {
                    val others = currentUser.data?.toMutableMap() ?: mutableMapOf()
                    others[property.propertyName] = property.propertyValue.toString()
                    currentUser.data = others
                }
            }
            scope.user = currentUser
        }
    }

    override fun setUserPropertyOnce(property: UserProperty) {
        Sentry.configureScope { scope ->
            val currentUser = scope.user ?: User()
            val shouldSet = when (property.propertyName.lowercase()) {
                "email" -> currentUser.email == null
                "username" -> currentUser.username == null
                "ip_address", "ipaddress" -> currentUser.ipAddress == null
                else -> {
                    val others = currentUser.data
                    others == null || !others.containsKey(property.propertyName)
                }
            }

            if (shouldSet) {
                setUserProperty(property)
            }
        }
    }

    override fun pushAll() {
        Sentry.flush(2000)
    }
}

