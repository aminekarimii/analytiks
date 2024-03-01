package com.analytiks.addon.appvisor.ui

import com.analytiks.addon.appvisor.R
import com.analytiks.core.EventLog
import com.analytiks.core.VisorEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val DATE_FORMAT = "HH:mm MMM dd, yyyy"

data class VisorHistoryUi(
    val clients: List<String>,
    val event: EventLog,
    val date: String = getCurrentDate()
) {
    val addonIcons: List<Int>
        get() = clients.map { getAddonLogoIcon(it) }

    companion object {
        fun from(event: VisorEvent): VisorHistoryUi {
            return VisorHistoryUi(
                clients = event.clients,
                event = event.type
            )
        }

        fun getCurrentDate(): String {
            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            return simpleDateFormat.format(calendar.time)
        }
    }

    private fun getAddonLogoIcon(client: String): Int {
        return when (client) {
            "AmplitudeAnalyticsClient" -> R.drawable.amplitude_logo
            "MixpanelAnalyticsClient" -> R.drawable.mixpanel_logo
            "SegmentAnalyticsClient" -> R.drawable.segment_logo
            else -> android.R.drawable.btn_star
        }
    }
}
