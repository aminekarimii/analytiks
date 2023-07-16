package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.timber.TimberLocalClient
import com.analytiks.core.CoreAddon
import com.analytiks.core.model.Param
import com.analytiks.segment.SegmentAnalyticsClient
import com.logitanalyticsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var analytiks: Analytiks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val clients: List<CoreAddon> = listOf(
            TimberLocalClient(),
            MixpanelAnalyticsClient(
                token = "YOUR_TOKEN"
            ),
            SegmentAnalyticsClient(
                token = "YOUR_TOKEN",
                flushIntervalInSeconds = 5,
                trackApplicationLifecycleEvents = true,
            )
        )

        analytiks = Analytiks(clients)

        with(analytiks) {
            initialize(this@MainActivity.applicationContext)
            reset()
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