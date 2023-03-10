package com.analytiks.core.formatters

import com.analytiks.core.model.Param
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

class Formatters {
    private val strategy = JSONFormatStrategy()

    @Test
    fun `formatProperties should return JSONObject with correct properties`() {
        val eventProps = arrayOf(
            Param("key1", "value1"),
            Param("key2", "value2"),
            Param("key3", "value3")
        )

        val expectedJson = JSONObject(mapOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3"
        ))

        val actualJson = strategy(*eventProps)

        assertEquals(expectedJson.toString(), actualJson.toString())
    }
}