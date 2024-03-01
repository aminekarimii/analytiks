package com.analytiks.core

interface AnalytiksAppVisorInterceptor {
    fun intercept(event: VisorEvent)
}

sealed class EventLog(val message: String? = null) {
    class Event(val name: String) : EventLog()
    object Reset : EventLog("Reset Addons")
    object InitializeService : EventLog("Service Initialized")
    object UserIdentification : EventLog("User identified")
    object UserPropertyUpdate : EventLog("User property updated")
    object PushEvents : EventLog("Events Pushed")
}

data class VisorEvent(
    val clients: List<String>,
    val type: EventLog,
)