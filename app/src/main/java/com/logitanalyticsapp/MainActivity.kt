package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.appvisor.ui.helper.AppVisor
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.logitanalyticsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var analytiks: Analytiks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        AppVisor.createShortcut(this.applicationContext)

        analytiks.initialize(this@MainActivity.applicationContext)

        binding.content.logReset.setOnClickListener {
            analytiks.reset()
        }

        binding.content.logSimpleEventButton.setOnClickListener {
            analytiks.logEvent(name = "click_button")
        }

        binding.content.logEventWithProps.setOnClickListener {
            analytiks.logEvent(name = "click_properties_button", listOf(Param("param1", "value1")))
        }

        binding.content.logProperties.setOnClickListener {
            analytiks.setUserProperty(
                property = UserProperty(
                    propertyName = "email",
                    propertyValue = "test123@gmail.com"
                )
            )
        }
    }

    override fun onPause() {
        super.onPause()
        analytiks.pushAll()
    }
}