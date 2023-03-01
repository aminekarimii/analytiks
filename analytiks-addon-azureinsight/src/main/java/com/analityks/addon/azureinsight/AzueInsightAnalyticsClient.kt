package com.analityks.addon.azureinsight

import android.app.Application
import android.content.Context
import com.analytiks.core.BaseAnalytics
import com.analytiks.core.ConfigurationFile
import com.analytiks.core.EventsExtension
import com.analytiks.core.formatters.MapFormatStrategy
import com.analytiks.core.model.EventProperty
import com.microsoft.applicationinsights.library.ApplicationInsights
import com.microsoft.applicationinsights.library.TelemetryClient


const val TAG = "AzureInsightAnalyticsClient"

class AzureInsightConfigurationProps(
    instrumentationKey: String,
) : ConfigurationFile(instrumentationKey)

class AzureInsightAnalyticsClient(
    private val configuration: AzureInsightConfigurationProps
) : BaseAnalytics, EventsExtension {

    private lateinit var mapFormatter: MapFormatStrategy

    private val telemetryClient: TelemetryClient by lazy {
        TelemetryClient.getInstance()
    }

    override fun initialize(context: Context) {
        ApplicationInsights.setup(
            context,
            context as Application,
            configuration.token
        )
    }

    override fun logEvent(name: String) {
        telemetryClient.trackEvent(name)
    }

    override fun logEvent(name: String, vararg properties: EventProperty) {
        telemetryClient.trackEvent(name, mapFormatter(*properties))
    }

}