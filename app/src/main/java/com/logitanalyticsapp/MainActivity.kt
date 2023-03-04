package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.addon.timber.TimberLocalClient
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

        analytiks = Analytiks(clients).also {
            it.initialize(this@MainActivity)
        }

        analytiks.logFirstEvent()

        binding.fab.setOnClickListener {
            analytiks.logEventOnClick()
        }
    }

    private fun Analytiks.logFirstEvent() {
        this.logEvent(
            name = "event_name",
            excludedAddons = listOf(MixpanelAnalyticsClient::class.java),
            properties = arrayOf(
                Param("val-name", "val-value")
            )
        )
    }

    private fun Analytiks.logEventOnClick() {
        this.logEvent(
            name = "button_click",
            excludedAddons = listOf(MixpanelAnalyticsClient::class.java)
        )
    }
}