package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.core.model.Param
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
            Param("val-name", "val-value"),
            excludedAddons = listOf(MixpanelAnalyticsClient::class.java)
        )

        binding.fab.setOnClickListener {
            analytiks.logEvent(
                "button-click",
                Param(propertyName = "val-name", propertyValue = "val-value")
            )
        }
    }

}