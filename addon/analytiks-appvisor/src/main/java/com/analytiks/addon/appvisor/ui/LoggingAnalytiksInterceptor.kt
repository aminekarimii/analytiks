package com.analytiks.addon.appvisor.ui

import android.util.Log
import com.analytiks.core.AnalytiksAppVisorInterceptor

class LoggingAnalytiksInterceptor : AnalytiksAppVisorInterceptor {

    override fun intercept(methodName: String) {
        println("Method called: $methodName")
        if (methodName == "logEvent") {
            Log.d("GlobalTag", "logEvent called");
        }
    }
}