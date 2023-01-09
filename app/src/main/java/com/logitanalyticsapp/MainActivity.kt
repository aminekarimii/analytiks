package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.logitanalyticsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var analytiks: Analytiks
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        analytiks = Analytiks(
            listOf(
                MixpanelAnalyticsClient()
            )
        )

        analytiks.initialize()
        analytiks.logEvent("event_name")

        binding.fab.setOnClickListener { view ->
           analytiks.logEvent(view.id.toString())
        }
    }

}