package com.analytiks

import android.os.Bundle
import com.analityks.addon.azureinsight.AzureInsightAnalyticsClient
import com.analytiks.addon.mixpanel.GoogleAnalyticsClient
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.timber.TimberLocalClient
import org.json.JSONObject

object AnalytiksFactory {

    @JvmStatic
    fun createAzureInsightAnalyticsClient(instrumentationKey: String): AzureInsightAnalyticsClient {
        return AzureInsightAnalyticsClient(instrumentationKey)
    }

    @JvmStatic
    fun createGoogleAnalyticsClient(
        isAnalyticsCollectionEnabled: Boolean = true,
        sessionTimeoutDuration: Long? = null,
        defaultEventParameters: Bundle? = null,
    ): GoogleAnalyticsClient {
        return GoogleAnalyticsClient(
            isAnalyticsCollectionEnabled, sessionTimeoutDuration, defaultEventParameters
        )
    }

    @JvmStatic
    fun createMixpanelAnalyticsClient(
        token: String,
        optOutTrackingDefault: Boolean = false,
        superProperties: JSONObject? = null,
        instanceName: String? = null,
        trackAutomaticEvents: Boolean = true,
    ): MixpanelAnalyticsClient {
        return MixpanelAnalyticsClient(
            token,
            optOutTrackingDefault,
            superProperties,
            instanceName,
            trackAutomaticEvents
        )
    }

    @JvmStatic
    fun createTimberLocalClient(): TimberLocalClient {
        return TimberLocalClient()
    }

}
