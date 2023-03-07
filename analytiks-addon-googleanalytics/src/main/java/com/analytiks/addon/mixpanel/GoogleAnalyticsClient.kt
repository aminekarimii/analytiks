package com.analytiks.addon.mixpanel

import android.content.Context
import android.os.Bundle
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.BundleFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase


const val TAG = "GoogleAnalyticsClient"

class GoogleAnalyticsClient(
    private val isAnalyticsCollectionEnabled: Boolean = true,
    private val sessionTimeoutDuration: Long? = null,
    private val defaultEventParameters: Bundle? = null,
) : BaseAnalytics, EventsExtension, UserProfileExtension {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val formatter = BundleFormatStrategy()

    override fun initialize(context: Context) {
        firebaseAnalytics = Firebase.analytics.apply {
            setAnalyticsCollectionEnabled(isAnalyticsCollectionEnabled)
            defaultEventParameters?.let { setDefaultEventParameters(it) }
            sessionTimeoutDuration?.let { setSessionTimeoutDuration(it) }
        }
    }

    override fun logEvent(name: String) {
        firebaseAnalytics.logEvent(name, null)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        firebaseAnalytics.logEvent(name, formatter(*properties))
    }

    override fun identify(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(property: UserProperty) {
        firebaseAnalytics.setUserProperty(property.propertyName, property.propertyValue?.toString())
    }

    override fun reset() {
        firebaseAnalytics.resetAnalyticsData()
    }
}