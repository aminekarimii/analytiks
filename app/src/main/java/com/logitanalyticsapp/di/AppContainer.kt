package com.logitanalyticsapp.di

import com.analytiks.Analytiks
import com.analytiks.AnalytiksFactory
import com.analytiks.core.CoreAddon

class AppContainer {
    private val clients: List<CoreAddon> = listOf(
        AnalytiksFactory.createTimberLocalClient(),
        AnalytiksFactory.createMixpanelAnalyticsClient(
            token = "test-key-goes-here",
            optOutTrackingDefault = true,
        ),
    )

    val analytiks = Analytiks(clients)
}