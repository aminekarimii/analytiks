package com.logitanalyticsapp

import android.app.Application
import android.util.Log
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.timber.TimberLocalClient
import com.analytiks.core.model.Param

class AnalytiksApplication : Application() {
    private lateinit var analytiks: Analytiks

    override fun onCreate() {
        super.onCreate()

        Log.d("test", "hello world")

        val clients = listOf(
            TimberLocalClient(),
            MixpanelAnalyticsClient(
                token = "test-key-goes-here",
                optOutTrackingDefault = true,
            ),
        )

        analytiks = Analytiks(clients)
        analytiks.initialize(this)

        analytiks.logEvent(
            name = "event_name",
            properties = arrayOf(
                Param("val-name", "val-value")
            )
        )
    }

}