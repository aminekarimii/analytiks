package com.analytiks.addon.amplitude

import com.amplitude.core.ServerZone

enum class ServerGeoZone(val serverZone: ServerZone) {
    US(ServerZone.US),
    EU(ServerZone.EU)
}