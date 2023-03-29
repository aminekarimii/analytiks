package com.logitanalyticsapp.di

import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.timber.TimberLocalClient
import com.analytiks.core.CoreAddon

class AppContainer {
    private val clients: Sequence<CoreAddon> = sequenceOf(
        TimberLocalClient(),
        MixpanelAnalyticsClient(
            token = "test-key-goes-here",
            optOutTrackingDefault = true,
        )
    )
    val analytiks = Analytiks(clients)
}