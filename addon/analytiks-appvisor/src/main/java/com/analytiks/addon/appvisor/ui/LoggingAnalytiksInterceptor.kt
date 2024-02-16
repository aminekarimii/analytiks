package com.analytiks.addon.appvisor.ui

import com.analytiks.core.AnalytiksAppVisorInterceptor
import com.analytiks.core.EventLog
import com.analytiks.core.VisorEvent

class LoggingAnalytiksInterceptor(
    private val collector: AppVisorDataCollector
) : AnalytiksAppVisorInterceptor {

    override fun intercept(methodName: VisorEvent) {
        println("Method called: $methodName")
        if (methodName.type is EventLog.Event) {
            collector.addEvent(methodName)
        }
    }
}