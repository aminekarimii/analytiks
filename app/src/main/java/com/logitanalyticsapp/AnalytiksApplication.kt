package com.logitanalyticsapp

import android.app.Application
import com.analytiks.Analytiks
import com.logitanalyticsapp.di.AppContainer

class AnalytiksApplication : Application() {
    lateinit var appContainer: AppContainer
    private lateinit var analytiks: Analytiks

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer()

        analytiks = appContainer.analytiks

        analytiks.initialize(this)
    }

}