package com.analytiks.segment

import android.content.Context
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.analytiks.segment.PropertiesHelper.formatParamsToProperties
import com.segment.analytics.Analytics
import com.segment.analytics.Traits
import java.util.concurrent.TimeUnit

class SegmentAnalyticsClient(
    private val token: String,
    private val recordScreen: Boolean = true,
    private val collectDeviceId: Boolean = true,
    private val flushIntervalInSeconds: Long? = null,
    private val tag: String? = null
) : CoreAddon, EventsExtension, UserProfileExtension {
    lateinit var segmentAnalytics: Analytics

    override fun initialize(context: Context) {
        segmentAnalytics = Analytics.Builder(context, token).apply {
            trackApplicationLifecycleEvents()

            if (flushIntervalInSeconds != null) this.flushInterval(
                flushIntervalInSeconds,
                TimeUnit.SECONDS
            )

            if (recordScreen) this.recordScreenViews()
            if (collectDeviceId) this.collectDeviceId(true)
            tag?.let { this.tag(it) }
        }.build().also {
            Analytics.setSingletonInstance(it)
        }

        segmentAnalytics = Analytics.with(context)
    }

    override fun reset() {
        segmentAnalytics.reset()
    }

    override fun logEvent(name: String) {
        segmentAnalytics.track(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        val formattedParams = formatParamsToProperties(*properties)
        segmentAnalytics.track(name, formattedParams)
    }

    override fun identify(userId: String) {
        segmentAnalytics.identify(userId)
    }

    override fun setUserProperty(property: UserProperty) {
        segmentAnalytics.identify(
            Traits().apply {
                putValue(property.propertyName, property.propertyValue)
            }
        )
    }

    override fun setUserPropertyOnce(property: UserProperty) = Unit
}