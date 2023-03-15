package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.core.model.Param
import com.logitanalyticsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val appContainer = (application as AnalytiksApplication).appContainer

        appContainer.analytiks.logFirstEvent()

        binding.fab.setOnClickListener {
            appContainer.analytiks.logEventOnClick()
        }
    }

    private fun Analytiks.logFirstEvent() {
        this.logEvent(
            name = "event_name",
            excludedAddons = setOf(MixpanelAnalyticsClient::class.java),
            properties = listOf(
                Param("val-name", "val-value")
            )
        )
    }

    private fun Analytiks.logEventOnClick() {
        this.logEvent(
            name = "button_click",
            excludedAddons = setOf(MixpanelAnalyticsClient::class.java)
        )
    }
}