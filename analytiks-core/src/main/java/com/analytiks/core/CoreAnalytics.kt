package com.analytiks.core

import java.util.*

data class UserProperty(
    val propertyName: String,
    val propertyValue: Any?
)

abstract class BaseAnalytics {
    abstract fun initialize()
}

interface EventsExtension {
    fun logEvent(name: String)
}

interface UserProfileExtension {
    fun identify(userId: String? = UUID.randomUUID().toString())
    fun setProperty(property: UserProperty)

}
