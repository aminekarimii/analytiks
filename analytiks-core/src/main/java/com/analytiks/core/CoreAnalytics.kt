package com.analytiks.core

import android.content.Context
import android.os.Bundle
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import java.util.*


interface CoreAddon {
    fun initialize(context: Context)

    fun reset()
}

interface EventsExtension {
    fun logEvent(name: String)
    fun logEvent(name: String, vararg properties: Param)
}

interface UserProfileExtension {
    fun identify(userId: String? = UUID.randomUUID().toString())
    fun setUserProperty(property: UserProperty)
}

interface PropertiesFormatterExtension {
    fun formatProps(): Bundle
}
