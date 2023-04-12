package com.analytiks.core.formatters

import com.analytiks.core.model.Param
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

class Formatters {

    private val eventProps = arrayOf(
        Param("key1", "value1"),
        Param("key2", "value2"),
        Param("key3", "value3")
    )

    private val jsonStrategy = JSONFormatStrategy()
    private val mapStrategy = MapFormatStrategy()

    @Test
    fun `formatProperties should return JSONObject with correct properties`() {
        val expectedJson = JSONObject(
            mapOf(
                "key1" to "value1",
                "key2" to "value2",
                "key3" to "value3"
            )
        )

        val actualJson = jsonStrategy(*eventProps)

        assertEquals(expectedJson.toString(), actualJson.toString())
    }

    @Test
    fun `mapStrategy should return Map with correct properties`() {
        val expectedMap = mapOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3"
        )

        val actualMapProperties = mapStrategy(*eventProps)

        assertEquals(expectedMap.toString(), actualMapProperties.toString())
    }

}