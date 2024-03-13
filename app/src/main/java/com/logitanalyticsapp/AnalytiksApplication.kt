package com.logitanalyticsapp

import android.app.Application
import com.analytiks.addon.appvisor.ui.helper.AnalytiksVisor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnalytiksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AnalytiksVisor.createShortcut(this)
    }
}