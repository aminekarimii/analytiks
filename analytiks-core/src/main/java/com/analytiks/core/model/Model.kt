package com.analytiks.core.model


data class UserProperty(
    val propertyName: String,
    val propertyValue: Any?
)

data class EventProperty(
    val propertyName: String,
    val propertyValue: String,
)