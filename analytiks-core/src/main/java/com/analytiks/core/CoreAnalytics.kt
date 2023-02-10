package com.analytiks.core

import android.content.Context
import android.os.Bundle
import com.analytiks.core.model.EventProperty
import java.util.*

data class UserProperty(
    val propertyName: String,
    val propertyValue: Any?
)

interface BaseAnalytics {
    fun initialize(context: Context)
}

interface EventsExtension {
    fun logEvent(name: String, vararg properties: EventProperty)
}

interface UserProfileExtension {
    fun identify(userId: String? = UUID.randomUUID().toString())
    fun setProperty(property: UserProperty)
}

interface PropertiesFormatterExtension {
    fun formatProps(): Bundle
}
