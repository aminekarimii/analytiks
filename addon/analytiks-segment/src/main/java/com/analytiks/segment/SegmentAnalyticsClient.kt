package com.analytiks.segment

import android.content.Context
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

const val TAG = "SegmentAnalyticsClient"

class SegmentAnalyticsClient(
    private val token: String,
    private val collectDeviceId: Boolean = true,
    private val trackApplicationLifecycleEvents: Boolean = false,
    private val useLifecycleObserver: Boolean = false,
    private val flushIntervalInSeconds: Int? = null,
    private val flushAt: Int? = null,
    private val tag: String? = null
) : CoreAddon, EventsExtension, UserProfileExtension, AnalyticsDataTransmitterExtension {
    lateinit var segmentAnalytics: Analytics

    override fun initialize(context: Context) {
        segmentAnalytics = Analytics(this.token, context) {
            this.trackApplicationLifecycleEvents =
                this@SegmentAnalyticsClient.trackApplicationLifecycleEvents

            this.useLifecycleObserver = this@SegmentAnalyticsClient.useLifecycleObserver
            this.collectDeviceId = this@SegmentAnalyticsClient.collectDeviceId

            this@SegmentAnalyticsClient.flushIntervalInSeconds?.let { this.flushInterval = it }
            this@SegmentAnalyticsClient.flushAt?.let { this.flushAt = it }
        }
    }

    override fun reset() {
        segmentAnalytics.reset()
    }

    override fun logEvent(name: String) {
        segmentAnalytics.track(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        segmentAnalytics.track(name, buildJsonObject {
            properties.forEach {
                put(it.propertyName, it.propertyValue)
            }
        })
    }

    override fun identify(userId: String) {
        segmentAnalytics.identify(userId)
    }

    override fun setUserProperty(property: UserProperty) {
        if (segmentAnalytics.userId() == null) return

        segmentAnalytics.identify(userId = segmentAnalytics.userId()!!, buildJsonObject {
            put(property.propertyName, property.propertyValue.toString())
        })
    }

    override fun setUserPropertyOnce(property: UserProperty) = Unit

    override fun pushAll() {
        segmentAnalytics.flush()
    }
}