package com.analytiks.core.formatters

import com.analytiks.core.model.Param
import org.json.JSONObject

abstract class FormatEventPropertiesStrategy<T> {
    operator fun invoke(vararg eventProperties: Param): T {
        return this.formatProperties(*eventProperties)
    }

    abstract fun formatProperties(vararg eventProperties: Param): T
}

class JSONFormatStrategy : FormatEventPropertiesStrategy<JSONObject>() {
    override fun formatProperties(vararg eventProperties: Param): JSONObject {
        val mapProps = mutableMapOf<String?, Any?>()
        eventProperties.forEach {
            mapProps[it.propertyName] = it.propertyValue
        }

        return JSONObject(mapProps)
    }
}

class MapFormatStrategy : FormatEventPropertiesStrategy<Map<String, String>>() {
    override fun formatProperties(vararg eventProperties: Param): Map<String, String> {
        return eventProperties.associate { property ->
            property.propertyName to property.propertyName
        }
    }
}