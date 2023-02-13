package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.mixpanel.MixpanelConfigurationProps
import com.analytiks.core.model.EventProperty
import com.logitanalyticsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var analytiks: Analytiks
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val clients = listOf(
            MixpanelAnalyticsClient(
                MixpanelConfigurationProps("test-key-goes-here", true)
            ),
        )

        analytiks = Analytiks(clients)

        analytiks.initialize(this)
        analytiks.logEvent(
            "event_name", EventProperty(propertyName = "val-name", propertyValue = "val-value")
        )

        binding.fab.setOnClickListener { view ->
            analytiks.logEvent(
                "button-click",
                EventProperty(propertyName = "val-name", propertyValue = "val-value")
            )
        }
    }

}