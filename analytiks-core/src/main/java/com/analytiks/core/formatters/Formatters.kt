package com.analytiks.core.formatters

import com.analytiks.core.model.EventProperty
import org.json.JSONObject

abstract class FormatEventPropertiesStrategy<T> {
    operator fun invoke(vararg eventProperties: EventProperty?): T {
        return this.formatProperties(*eventProperties)
    }

    abstract fun formatProperties(vararg eventProperties: EventProperty?): T
}

class JSONFormatStrategy : FormatEventPropertiesStrategy<JSONObject?>() {
    override fun formatProperties(vararg eventProperties: EventProperty?): JSONObject {
        val mapProps = mutableMapOf<String?, Any?>()
        eventProperties.forEach {
            mapProps[it?.propertyName] = it?.propertyValue
        }

        return JSONObject(mapProps)
    }
}

class MapFormatStrategy : FormatEventPropertiesStrategy<Map<String, String>>() {
    override fun formatProperties(vararg eventProperties: EventProperty?): Map<String, String> {
        return eventProperties.associate { property ->
            property!!.propertyName to property.propertyName
        }
    }
}