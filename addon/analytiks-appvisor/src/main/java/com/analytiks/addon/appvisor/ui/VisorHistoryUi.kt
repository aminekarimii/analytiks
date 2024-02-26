package com.analytiks.addon.appvisor.ui

import com.analytiks.core.EventLog
import com.analytiks.core.VisorEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class VisorHistoryUi(
    val clients: List<String>,
    val event: String,
    val date: String = SimpleDateFormat(
        "dd MMM 'at' HH:mm:ss",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
) {
    val displayClients = clients.joinToString { it }

    companion object {
        fun from(event: VisorEvent): VisorHistoryUi {
            val eventHistory = when (event.type) {
                is EventLog.Event -> {
                    val eventData = (event.type as EventLog.Event)
                    buildString {
                        append("New Event: ${eventData.name}")
                        if (eventData.properties.isNotEmpty()) {
                            append("\n")
                            append("Properties: ")
                            append(eventData.properties.joinToString { param ->
                                "${param.propertyName} : ${param.propertyValue}"
                            })
                        }
                    }
                }

                EventLog.Reset -> "Reset"
                EventLog.InitializeService -> "Service initialized"
                EventLog.PushEvents -> "Push events"
                EventLog.UserIdentification -> "User identified"
                EventLog.UserPropertyUpdate -> "User property updated"
            }

            return VisorHistoryUi(
                clients = event.clients,
                event = eventHistory
            )
        }
    }
}
