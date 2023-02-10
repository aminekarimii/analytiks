package com.analytiks.core.formatters

import com.analytiks.core.model.EventProperty
import org.json.JSONObject

interface FormatEventPropertiesStrategy<T> {
    fun formatProperties(vararg eventProperties: EventProperty?): T
}

class JSONFormatStrategy : FormatEventPropertiesStrategy<JSONObject?> {
    operator fun invoke(vararg eventProperties: EventProperty?): JSONObject {
        return this.formatProperties(*eventProperties)
    }

    override fun formatProperties(vararg eventProperties: EventProperty?): JSONObject {
        val mapProps = mutableMapOf<String?, Any?>()
        eventProperties.forEach {
            mapProps[it?.propertyName] = it?.propertyValue
        }

        return JSONObject(mapProps)
    }
}