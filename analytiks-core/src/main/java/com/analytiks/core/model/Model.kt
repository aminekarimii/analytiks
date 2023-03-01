package com.analytiks.core.model


data class UserProperty(
    val propertyName: String,
    val propertyValue: Any?
)

data class Param(
    val propertyName: String,
    val propertyValue: String,
)