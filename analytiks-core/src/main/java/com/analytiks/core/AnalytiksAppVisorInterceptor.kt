package com.analytiks.core

import com.analytiks.core.model.Param

interface AnalytiksAppVisorInterceptor {
    fun intercept(event: VisorEvent)
}

sealed class EventLog {
    class Event(val properties: List<Param>) : EventLog()
    object Reset : EventLog()
    object InitializeService : EventLog()
    object UserIdentification : EventLog()
    object UserPropertyUpdate : EventLog()
    object PushEvents : EventLog()
}

data class VisorEvent(
    val type: EventLog,
)