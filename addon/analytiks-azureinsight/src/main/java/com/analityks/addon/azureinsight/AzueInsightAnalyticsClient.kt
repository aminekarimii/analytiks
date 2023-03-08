package com.analityks.addon.azureinsight

import android.app.Application
import android.content.Context
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.formatters.MapFormatStrategy
import com.analytiks.core.model.Param
import com.microsoft.applicationinsights.library.ApplicationInsights
import com.microsoft.applicationinsights.library.TelemetryClient


const val TAG = "AzureInsightAnalyticsClient"

class AzureInsightAnalyticsClient(
    private val instrumentationKey: String,
) : CoreAddon, EventsExtension {

    private lateinit var mapFormatter: MapFormatStrategy

    private val telemetryClient: TelemetryClient by lazy {
        TelemetryClient.getInstance()
    }

    override fun initialize(context: Context) {
        ApplicationInsights.setup(
            context,
            context as Application,
            instrumentationKey
        )
    }

    override fun logEvent(name: String) {
        telemetryClient.trackEvent(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        telemetryClient.trackEvent(name, mapFormatter(*properties))
    }

}