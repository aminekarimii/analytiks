package com.analytiks.segment

import com.analytiks.core.model.Param
import com.segment.analytics.Properties

object PropertiesHelper {
    fun formatParamsToProperties(vararg properties: Param) : Properties {
        return Properties().apply {
            properties.forEach { param ->
                putValue(param.propertyName, param.propertyValue)
            }
        }
    }
}