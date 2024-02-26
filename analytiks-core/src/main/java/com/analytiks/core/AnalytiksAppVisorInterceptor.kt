package com.analytiks.core

interface AnalytiksAppVisorInterceptor {
    fun intercept(event: VisorEvent)
}

sealed class EventLog {
    class Event(val name: String) : EventLog()
    object Reset : EventLog()
    object InitializeService : EventLog()
    object UserIdentification : EventLog()
    object UserPropertyUpdate : EventLog()
    object PushEvents : EventLog()
}

data class VisorEvent(
    val clients: List<String>,
    val type: EventLog,
)