package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.amplitude.AmplitudeClient
import com.analytiks.addon.amplitude.ServerGeoZone
import com.analytiks.addon.mixpanel.GoogleAnalyticsClient
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.timber.TimberLocalClient
import com.analytiks.core.CoreAddon
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.analytiks.segment.SegmentAnalyticsClient
import com.analytiks.addon.appvisor.ui.MainActivity
import com.logitanalyticsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var analytiks: Analytiks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        MainActivity.createShortcut(this.applicationContext)

        val clients: List<CoreAddon> = listOf(
            CustomAnalytiksAddon(),
            TimberLocalClient(),
            MixpanelAnalyticsClient(
                token = "YOUR_TOKEN"
            ),
            SegmentAnalyticsClient(
                token = "YOUR_TOKEN",
                flushIntervalInSeconds = 5,
                trackApplicationLifecycleEvents = true,
            ),
            GoogleAnalyticsClient(isAnalyticsCollectionEnabled = true),
            AmplitudeClient(
                token = "YOUR_TOKEN",
                // It is recommended to check your server zone and set it.
                serverGeoZone = ServerGeoZone.US
            )
        )

        analytiks = Analytiks(clients)

        with(analytiks) {
            initialize(this@MainActivity.applicationContext)
            setUserProperty(
                property = UserProperty(
                    propertyName = "prop1",
                    propertyValue = "test1"
                )
            )
        }


        binding.fab.setOnClickListener {
            analytiks.logEvent(
                name = "fab_button_click", properties = listOf(
                    Param(propertyName = "prop1", propertyValue = "val2")
                )
            )
        }
    }

    override fun onPause() {
        super.onPause()
        analytiks.pushAll()
    }
}