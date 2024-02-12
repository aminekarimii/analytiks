package com.logitanalyticsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.analytiks.Analytiks
import com.analytiks.addon.appvisor.ui.MainActivity
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

        MainActivity.createShortcut(this.applicationContext)

        analytiks.initialize(this@MainActivity.applicationContext)
        analytiks.setUserProperty(
            property = UserProperty(
                propertyName = "prop1",
                propertyValue = "test1"
            )
        )

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