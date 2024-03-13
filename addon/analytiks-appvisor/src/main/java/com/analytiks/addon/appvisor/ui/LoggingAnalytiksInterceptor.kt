package com.analytiks.addon.appvisor.ui

import com.analytiks.core.AnalytiksAppVisorInterceptor
import com.analytiks.core.VisorEvent

class LoggingAnalytiksInterceptor(
    private val collector: AppVisorDataCollector
) : AnalytiksAppVisorInterceptor {

    override fun intercept(event: VisorEvent) {
        collector.addEvent(event)
    }
}